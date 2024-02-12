package com.SpringBoot.Clinica.Jwt.Filter;

import com.SpringBoot.Clinica.Jwt.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;

public class JwtAuthorizationFilter extends OncePerRequestFilter {


    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JWTUtils jwtUtils;
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(JwtAuthenticationFilter jwtAuthenticationFilter, JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String Header = request.getHeader("Authorization");

        String token = null;
        String username = null;
        UserDetails userDetails = null;

        if(token != null && Header.startsWith("Bearer ")){
            token =  Header.substring(7,Header.length());
            username = this.jwtUtils.getSubject(token);
            userDetails = this.userDetailsService.loadUserByUsername(username);

            if(this.jwtUtils.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        this.jwtAuthenticationFilter.attemptAuthentication(request,response);

        filterChain.doFilter(request,response);

    }
}
