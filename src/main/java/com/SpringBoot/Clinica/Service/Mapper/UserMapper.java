package com.SpringBoot.Clinica.Service.Mapper;

import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    public UserEntity map(UserDTO userDTO) throws Exception;
    public UserEntity map(UserRequestDTO userDTO) throws Exception;
    public UserDTO map(UserEntity user) throws Exception;


}
