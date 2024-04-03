package com.SpringBoot.Clinica.Controller;

import com.SpringBoot.Clinica.Service.UserAuthenticateService;
import com.SpringBoot.Clinica.api.LoginApiDelegate;
import com.SpringBoot.Clinica.model.JWTResponseDTO;
import com.SpringBoot.Clinica.model.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
import java.util.Map;

public class LoginController implements LoginApiDelegate {

    private UserAuthenticateService userAuthenticateService;

    public LoginController(UserAuthenticateService userAuthenticateService) {
        this.userAuthenticateService = userAuthenticateService;
    }

    @Override
    public ResponseEntity<JWTResponseDTO> loginUser(LoginDTO loginDTO) {


        try {
            Map<String, String> tokenMap = this.userAuthenticateService.generateToken(loginDTO.getUsername(), loginDTO.getPassword());
            return responseTokens(tokenMap);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public ResponseEntity<JWTResponseDTO> refeshToken(String authorization) {
        Map<String,String> validateRefresh = this.userAuthenticateService.validateTokenRefresh(authorization);
        return responseTokens(validateRefresh);
    }


    private ResponseEntity<JWTResponseDTO> responseTokens(Map<String,String> tokenMap){
        JWTResponseDTO jwtResponseDTO = new JWTResponseDTO();

        jwtResponseDTO.setAccessToken(tokenMap.get("token:"));
        jwtResponseDTO.setRefreshToken(tokenMap.get("refresh:"));

        System.out.println(jwtResponseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(jwtResponseDTO);
    }
}
