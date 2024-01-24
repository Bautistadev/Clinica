package com.SpringBoot.Clinica.Service.Mapper;

import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;

public interface UserMapper {

    public UserEntity map(UserDTO user);
    public UserDTO map(UserRequestDTO user);
    public UserDTO map(UserEntity user);


}
