package com.SpringBoot.Clinica.Service;

import com.SpringBoot.Clinica.Jwt.JWTUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserAuthenticateService {

    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

    private PasswordEncoder passwordEncoder;

    public UserAuthenticateService(UserDetailsService userDetailsService, JWTUtils jwtUtils,PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, String > generateToken(String username, String password){

        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        Map<String, Object> Claims = new HashMap<>();
        Map<String, String> respose = new HashMap<>();
        System.out.println(user.getUsername());
        String token = null;
        String refresh = null;



        if(this.passwordEncoder.matches(password,user.getPassword())){
            Claims.put("role",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            token = this.jwtUtils.generateToken(user,Claims,1000000L);
            refresh = this.jwtUtils.generateToken(user,new HashMap<>(),1000000L);


            respose.put("token:",token);
            respose.put("refresh:", token);

        }

        return respose;

    }

    public Map<String,String> validateTokenRefresh(String token){
        String username = this.jwtUtils.getSubject(token);
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        Map<String,String> response =  generateToken(user.getUsername(),user.getPassword());

        return response;
    }
}
