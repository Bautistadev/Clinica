package com.SpringBoot.Clinica.Service.Mapper;

import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;

import org.apache.el.parser.BooleanNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class UserMapperImplements implements UserMapper{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMapperImplements.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserMapperImplements() {
    }

    @Override
    public UserEntity map(UserDTO userDTO) throws Exception {

        try {
            UserEntity user = UserEntity
                    .builder()
                    .id(userDTO.getId())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .dateCreated(userDTO.getDateCreated())
                    .build();

            /**
             * SET ROL
             * */
            if (CompareEnums(userDTO.getRole(), Role.ADMIN))
                user.setRole(Role.ADMIN);
            else
                user.setRole(Role.USER);

            /**
             * SET STATUS
             * */
            if (CompareEnums(userDTO.getStatus(), Status.ENABLE))
                user.setStatus(Status.ENABLE);
            else if (CompareEnums(userDTO.getStatus(), Status.DISABLE))
                user.setStatus(Status.DISABLE);
            else
                user.setStatus(Status.SUSPENDED);

            /**
             * SET DELETE DATE
             * */
            if (userDTO.getDateDeleted() != null)
                user.setDateDeleted(userDTO.getDateDeleted());

            LOGGER.trace(String.format("Info : UserMapperImplements : map : UserDTO -> UserEntity : " + LocalDateTime.now() + " : ", user));

            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.error(String.format("Error : UserMapperImplements : map : UserDTO -> UserEntity : " + LocalDateTime.now() ));
            throw new Exception("Error : map : UserDTO -> UserEntity ");
        }
    }

    @Override
    public UserEntity map(UserRequestDTO userDTO) throws Exception {
        try {
            UserEntity user = UserEntity
                    .builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .dateCreated(userDTO.getDateCreated())
                    .build();

            /**
             * SET ROL
             * */
            if (CompareEnums(userDTO.getRole(), Role.ADMIN))
                user.setRole(Role.ADMIN);
            else
                user.setRole(Role.USER);

            /**
             * SET STATUS
             * */
            if (CompareEnums(userDTO.getStatus(), Status.ENABLE))
                user.setStatus(Status.ENABLE);
            else if (CompareEnums(userDTO.getStatus(), Status.DISABLE))
                user.setStatus(Status.DISABLE);
            else
                user.setStatus(Status.SUSPENDED);

            /**
             * SET DELETE DATE
             * */
            if (userDTO.getDateDeleted() != null)
                user.setDateDeleted(userDTO.getDateDeleted());

            LOGGER.trace(String.format("Info : UserMapperImplements : map : UserDTO -> UserEntity : " + LocalDateTime.now() + " : ", user));

            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.error(String.format("Error : UserMapperImplements : map : UserDTO -> UserEntity : " + LocalDateTime.now() ));
            throw new Exception("Error : map : UserRequestDTO -> UserEntity ");
        }
    }

    @Override
    public UserDTO map(UserEntity user) throws Exception {
        try {
            UserDTO userDTO = new UserDTO()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .dateCreated(user.getDateCreated());

            /**
             * SET ROL
             * */
            if (CompareEnums(user.getRole(), UserDTO.RoleEnum.ADMIN))
                userDTO.setRole(UserDTO.RoleEnum.ADMIN);
            else
                userDTO.setRole(UserDTO.RoleEnum.USER);

            /**
             * SET STATUS
             * */
            if (CompareEnums(user.getStatus(), UserDTO.StatusEnum.ENABLE))
                userDTO.setStatus(UserDTO.StatusEnum.ENABLE);
            else if (CompareEnums(user.getStatus(), Status.DISABLE))
                userDTO.setStatus(UserDTO.StatusEnum.DISABLE);
            else
                userDTO.setStatus(UserDTO.StatusEnum.SUSPENDED);

            /**
             * SET DELETE DATE
             * */
            if (user.getDateDeleted() != null)
                userDTO.setDateDeleted(userDTO.getDateDeleted());

            LOGGER.trace(String.format("Info : UserMapperImplements : map : UserDTO -> UserEntity : " + LocalDateTime.now() + " : ", user));

            return userDTO;
        }catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.error(String.format("Error : UserMapperImplements : map : UserDTO -> UserEntity : " + LocalDateTime.now() ));
            throw new Exception("Error : map : UserEntity -> UserDTO");
        }
    }


    private Boolean CompareEnums(Object ob1,Object ob2){

        if(ob1.toString().equals(ob2.toString()))
            return true;
        return false;
    }
}

