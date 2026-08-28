package donation_center_service.com.bloodlink.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final HeaderAuthenticationFilter headerAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.GET, "/centers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/centers/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/centers/*/slots").permitAll()

                        // corrigido: /** para casar com /centers/created e qualquer subpath futuro
                        .requestMatchers(HttpMethod.POST, "/centers/**")
                        .hasRole("ADMIN_HEMOCENTRO")

                        .requestMatchers(HttpMethod.GET,
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()

                        .anyRequest().denyAll()
                )
                .addFilterBefore(headerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
