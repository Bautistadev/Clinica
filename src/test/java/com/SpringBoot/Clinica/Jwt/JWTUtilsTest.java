package com.SpringBoot.Clinica.Jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class JWTUtilsTest {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * Simple token (Claims are not necessary)
     * */
    @Test
    void generateToken() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("test");

        String token = this.jwtUtils.generateToken(userDetails,100000L);


        assertTrue(token instanceof String);
        assertTrue(token.isEmpty() == false);
        assertTrue(this.jwtUtils.validateToken(token,userDetails));



    }

    /**
     *Token with Claims parameters
     * */
    @Test
    void testGenerateToken() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("test");
        Map<String,Object> Claims = new HashMap<>();

        Claims.put("roles",userDetails.getAuthorities());

        String token =  this.jwtUtils.generateToken(userDetails,Claims,100000L);

        assertTrue(token instanceof String);
        assertTrue(token.isEmpty() == false);
        assertTrue(this.jwtUtils.validateToken(token,userDetails));
        assertFalse(this.jwtUtils.getAllClaimsForToken(token).isEmpty());
    }
}