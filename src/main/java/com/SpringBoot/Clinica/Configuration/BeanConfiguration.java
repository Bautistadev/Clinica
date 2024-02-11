package com.SpringBoot.Clinica.Configuration;

import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Repository.UserRepository;
import com.SpringBoot.Clinica.Service.Mapper.UserMapper;
import com.SpringBoot.Clinica.Service.Mapper.UserMapperImplements;
import com.SpringBoot.Clinica.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
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
}
