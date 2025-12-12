package com.valeri.project_RBPO.config;

import com.valeri.project_RBPO.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter
{

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        final String authHeader = request.getHeader("Authorization");

        // Если заголовок начинается с "Bearer " - это JWT
        if (authHeader != null && authHeader.startsWith("Bearer "))
        {
            final String jwt = authHeader.substring(7);
            final String username = jwtTokenProvider.extractUsername(jwt);

            // Если пользователь ещё не аутентифицирован в этом запросе
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                var userDetails = userDetailsService.loadUserByUsername(username);

                // Проверяем, что это ACCESS токен и он валиден
                if ("ACCESS".equals(jwtTokenProvider.extractTokenType(jwt)) &&
                        jwtTokenProvider.isTokenValid(jwt, userDetails)) {

                    // Создаём объект аутентификации для Spring Security
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
                filterChain.doFilter(request, response);
    }
}
