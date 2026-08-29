package donation_center_service.com.bloodlink.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_ROLE_HEADER = "X-User-Role";
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        log.info("Filtro executado para: {} {}", request.getMethod(), request.getRequestURI());

        String userIdHeader = request.getHeader(USER_ID_HEADER);
        String roleHeader = request.getHeader(USER_ROLE_HEADER);

        log.info("Headers recebidos - X-User-Id: {}, X-User-Role: {}", userIdHeader, roleHeader);

        if (userIdHeader != null && roleHeader != null) {
            authenticate(userIdHeader, roleHeader);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(String userIdHeader, String roleHeader) {
        try {
            UUID userId = UUID.fromString(userIdHeader);
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleHeader));

            var authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Usuário autenticado: {} com role: {}", userId, roleHeader);
        } catch (IllegalArgumentException e) {
            log.warn("Header X-User-Id inválido recebido: {}", userIdHeader);
        }
    }
}