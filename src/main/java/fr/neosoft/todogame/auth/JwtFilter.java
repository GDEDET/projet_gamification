package fr.neosoft.todogame.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService service;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization");

        SecurityContext context = SecurityContextHolder.getContext();
        String username = null;
        String token;

        if (tokenHeader != null && tokenHeader.startsWith("Bearer")) {
            token = tokenHeader.substring(7);
            try{
                username = jwtTokenUtil.getUsernameFromToken(token);
            } catch (IllegalArgumentException e){
                log.warn("Impossible de lire le token");
            }
        } else {
            log.warn("Requete sans Authorization Header ou sans Bearer");
        }

        if (username!=null && context.getAuthentication() == null){
            UserDetails user = service.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user, null,
                    user.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);

    }
}
