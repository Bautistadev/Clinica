package com.SpringBoot.Clinica.Configuration;

import com.SpringBoot.Clinica.Jwt.Filter.JwtAuthenticationFilter;
import com.SpringBoot.Clinica.Jwt.Filter.JwtAuthorizationFilter;
import com.SpringBoot.Clinica.Jwt.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import java.security.Security;


@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,JwtAuthorizationFilter jwtAuthorizationFilter,JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/swagger-ui/**","/v3/api-docs").permitAll();
                    auth.requestMatchers("/api/v1/login/login","/api/v1/login/refresh").permitAll();
                    auth.requestMatchers("/test").hasAnyAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);




        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        return  http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JWTUtils jwtUtils(){
        return  new JWTUtils();
    }


    /**
     * Authentication filter
     * */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetailsService,JWTUtils jwtUtils,AuthenticationManager auth){
        JwtAuthenticationFilter jwtAuthentication = new JwtAuthenticationFilter(userDetailsService, jwtUtils);
        jwtAuthentication.setAuthenticationManager(auth);
        return jwtAuthentication;
    }

    /**
     *
     * Authorization filter
     * */
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(JwtAuthenticationFilter jwtAuthenticationFilter,JWTUtils jwtUtils,UserDetailsService userDetailsService){

        return  new JwtAuthorizationFilter(jwtAuthenticationFilter,jwtUtils,userDetailsService);
    }
}
