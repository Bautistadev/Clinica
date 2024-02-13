package com.SpringBoot.Clinica.Service;

import com.SpringBoot.Clinica.model.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


public class UserDetailsServiceImplements implements UserDetailsService {

    private UserService userService;

    public UserDetailsServiceImplements(UserService userService) {
        this.userService = userService;
    }

    @Value("${jwt.user.test.username}")
    private String usernameTest;
    @Value("${jwt.user.test.password}")
    private String userPasswordTest;
    @Value("${jwt.user.test.role}")
    private String role;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);
       if(username.equals(usernameTest))
            return userTest();

        UserDTO userRequest = this.userService.findByUsername(username);



        if(userRequest != null) {
            Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(userRequest.getRole().name()));
            UserDetails userDetails = new User(userRequest.getUsername(),userRequest.getPassword(),grantedAuthorities);
            return userDetails;
        }else{
            throw new UsernameNotFoundException("User not exist");
        }

    }

    private UserDetails userTest(){
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        UserDetails userDetails = new User(usernameTest,userPasswordTest,grantedAuthorities);
        return userDetails;
    }
}
