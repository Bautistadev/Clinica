package com.SpringBoot.Clinica.Jwt.Filter;

import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Jwt.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

    private UserEntity user;
    private String username;
    private String password;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JWTUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            user = new ObjectMapper().readValue(request.getInputStream(),UserEntity.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
