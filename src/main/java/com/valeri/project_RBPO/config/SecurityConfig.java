package com.valeri.project_RBPO.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig
{

    // Режим аутентификации
    private static final boolean USE_JWT = true; // true = JWT, false = Basic Auth

    // CSRF защита
    private static final boolean USE_CSRF = false; // true = CSRF включен, false = отключен

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        if (USE_JWT)
        {
            return buildJwtConfig(http);
        }
        else
        {
            return buildBasicAuthConfig(http);
        }
    }

    private SecurityFilterChain buildBasicAuthConfig(HttpSecurity http) throws Exception {

        if (USE_CSRF) {
            http.csrf(csrf -> csrf
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .ignoringRequestMatchers("/api/register")
            );
        } else {
            http.csrf(csrf -> csrf.disable());
        }

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/register", "/csrf-token").permitAll()
                        .requestMatchers("/api/orders/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/products/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/customers/**").hasRole("ADMIN")
                        .requestMatchers("/api/categories/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }

    private SecurityFilterChain buildJwtConfig(HttpSecurity http) throws Exception
    {

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/register", "/api/auth/**", "/csrf-token").permitAll()
                        .requestMatchers("/api/orders/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/products/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/customers/**").hasRole("ADMIN")
                        .requestMatchers("/api/categories/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }

    public static boolean isJwtEnabled()
    {
        return USE_JWT;
    }

    public static boolean isCsrfEnabled()
    {
        return USE_CSRF;
    }
}