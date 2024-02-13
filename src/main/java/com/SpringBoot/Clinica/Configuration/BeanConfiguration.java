package com.SpringBoot.Clinica.Configuration;

import com.SpringBoot.Clinica.Controller.ControllerTest;
import com.SpringBoot.Clinica.Controller.LoginController;
import com.SpringBoot.Clinica.Controller.UserController;
import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Jwt.JWTUtils;
import com.SpringBoot.Clinica.Repository.UserRepository;
import com.SpringBoot.Clinica.Service.Mapper.UserMapper;
import com.SpringBoot.Clinica.Service.Mapper.UserMapperImplements;
import com.SpringBoot.Clinica.Service.UserAuthenticateService;
import com.SpringBoot.Clinica.Service.UserDetailsServiceImplements;
import com.SpringBoot.Clinica.Service.UserService;
import com.SpringBoot.Clinica.api.LoginApiDelegate;
import com.SpringBoot.Clinica.api.UsersApiDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate){
        return  new UserRepository(jdbcTemplate);
    }

    @Bean
    public UserMapper userMapper(){
        return new UserMapperImplements();
    }

    @Bean
    public UserService userService(UserRepository userRepository){
        return new UserService(userRepository, userMapper());
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService){
        return new UserDetailsServiceImplements(userService);
    }

    @Bean
    public UserAuthenticateService userAuthenticateService(UserDetailsService userDetailsService, JWTUtils jwtUtils,PasswordEncoder passwordEncoder){
        return new UserAuthenticateService(userDetailsService,jwtUtils,passwordEncoder);
    }

    @Bean
    public LoginApiDelegate loginController(UserAuthenticateService userAuthenticateService){
        return new LoginController(userAuthenticateService);
    }

    @Bean
    public ControllerTest controllerTest(){
        return new ControllerTest();
    }

    @Bean
    public UsersApiDelegate userController(UserService userService){
        return new UserController(userService);
    }
}
