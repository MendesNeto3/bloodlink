package com.bloodlink.auth.service;

import com.bloodlink.auth.domain.exception.EmailAlreadyExistsException;
import com.bloodlink.auth.domain.exception.InvalidCredentialsException;
import com.bloodlink.auth.domain.exception.InvalidTokenException;
import com.bloodlink.auth.domain.exception.UserNotFoundException;
import com.bloodlink.auth.domain.mapper.UserMapper;
import com.bloodlink.auth.domain.model.Enums.UserRole;
import com.bloodlink.auth.domain.model.User;
import com.bloodlink.auth.dto.request.LoginRequest;
import com.bloodlink.auth.dto.request.RefreshRequest;
import com.bloodlink.auth.dto.request.RegisterRequest;
import com.bloodlink.auth.dto.response.TokenResponse;
import com.bloodlink.auth.dto.response.UserResponse;
import com.bloodlink.auth.messaging.UserEventPublisher;
import com.bloodlink.auth.repository.UserRepository;
import com.bloodlink.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String REFRESH_KEY_PREFIX = "refresh:";
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserEventPublisher userEventPublisher;
    private final StringRedisTemplate redisTemplate;
    private final UserMapper mapper;

    @Override
    public UserResponse register(RegisterRequest request) {
        return userRepository
                .existsByEmail(request.email())
                .map(user -> {
                    user.setName(request.name());
                    user.setEmail(request.email());
                    user.setPasswordHash(request.password());
                    user.setRole(UserRole.DOADOR);

                    User savedUser = userRepository.save(user);
                    userEventPublisher.publishUserRegistered(savedUser);
                    return userRepository.save(user);
                })
                .map(mapper::toResponse)
                .orElseThrow(() ->
                        new EmailAlreadyExistsException("Email already exists!" + request.email()));
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!" + request.email()));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid password!");
        }
        return generateToken(user);
    }

    @Override
    public TokenResponse refresh(RefreshRequest request) {
        String key = REFRESH_KEY_PREFIX + request.refreshToken;
        String userIdValue = redisTemplate.opsForValue().get(key);

        if (userIdValue == null) {
            throw new InvalidTokenException("Refresh token invalid or expired");
        }
        User user = userRepository.findById(UUID.fromString(userIdValue))
                .orElseThrow(() -> new InvalidTokenException("User not found!" + userIdValue));

        redisTemplate.delete(key);

        return generateToken(user);
    }

    @Override
    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found" + id));
        return mapper.toResponse(user);
    }

    public TokenResponse generateToken(User user) {
        String tokenAcess = jwtTokenProvider.generateAccessToken(user.getId(), user.getRole());

        String tokenRefresh = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
                REFRESH_KEY_PREFIX + tokenAcess,
                user.getId().toString(),
                REFRESH_TOKEN_TTL
        );

        return new TokenResponse(tokenAcess, tokenRefresh);
    }
}
