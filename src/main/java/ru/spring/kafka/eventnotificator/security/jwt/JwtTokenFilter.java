package ru.spring.kafka.eventnotificator.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtTokenManager jwtTokenManager;

    public JwtTokenFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.replace("Bearer ", "");

        Claims claims;
        try {
            claims = jwtTokenManager.getLoginFromToken(token);

            Integer userId = claims.get("id", Integer.class);
            String role = claims.get("role", String.class);

            if (userId == null || role == null) {
                log.warn("JWT token does not contain required claims");
                filterChain.doFilter(request, response);
                return;
            }

            UserPrincipal principal = new UserPrincipal(userId, role);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken
                            (
                                    principal,
                                    null,
                                    List.of(new SimpleGrantedAuthority(role))
                            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authenticationToken);

        } catch (Exception e) {
            log.error("Error while getting login from token", e);
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
