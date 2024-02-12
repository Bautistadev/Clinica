package com.SpringBoot.Clinica.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtils {

    @Value("${jwt.key}")
    private String jwtKey;

    private String doGenerate(String username, Map<String,Object> claims ,Long time){

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SignatureAlgorithm.HS384,jwtKey)
                .compact();
    }

    public Claims getAllClaimsForToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails, Long time){

        Map<String, Object> Claims = new HashMap<>();
        return doGenerate(userDetails.getUsername(),Claims,time);

    }

    public String generateToken(UserDetails userDetails,Map<String,Object> Claims, Long time) {
        return doGenerate(userDetails.getUsername(),Claims,time);
    }

    private <T>T retriveClaim(String token, Function<Claims,T> retrive){
        Claims claims = this.getAllClaimsForToken(token);

        return retrive.apply(claims);
    }


    public String getSubject(String token){
        return retriveClaim(token,Claims::getSubject);
    }
    public Date getIssuedAt(String token){
        return retriveClaim(token,Claims::getIssuedAt);
    }
    public Date getExpiration(String token){
        return retriveClaim(token,Claims::getExpiration);
    }

    public Boolean getSigningKey(String token){
        return Jwts.parser().isSigned(token);
    }

    public String getAlgorithm(String token){
        return Jwts.header().getCompressionAlgorithm();
    }
    public Boolean isExpired(String token){
        return this.getExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        return !this.isExpired(token) && userDetails.getUsername().equals(this.getSubject(token));
    }
}
