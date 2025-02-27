package com.pio.foodiepanda.filter;

import com.pio.foodiepanda.constants.JwtConstant;
import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.service.JwtService;
import com.pio.foodiepanda.service.impl.CustomUserDetails;
import com.pio.foodiepanda.service.impl.CustomUserDetailsService;
import com.pio.foodiepanda.utility.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@WebFilter(filterName = "JwtRequestFilter")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filters incoming HTTP requests to check for a valid JWT token in the Authorization header.
     * If a valid token is found, the user is authenticated and the security context is updated.
     * If the token is missing or invalid, the request is passed along the filter chain without authentication.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //fetching the token from cookies
        String token = CookieUtil.getValue(request, JwtConstant.JWT_COOKIE_NAME);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String username = jwtService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isValid(token, (CustomUserDetails) userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (ExpiredJwtException e) {
            CookieUtil.clear(response, JwtConstant.JWT_COOKIE_NAME, MessageConstant.LOCALHOST);
        }
        filterChain.doFilter(request, response);
    }
}
