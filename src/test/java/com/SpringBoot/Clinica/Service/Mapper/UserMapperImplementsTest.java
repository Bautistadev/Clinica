package com.SpringBoot.Clinica.Service.Mapper;

import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class UserMapperImplementsTest {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public PasswordEncoder passwordEncoder;



    /**
     * @Function: map
     * @Param: UserDTO
     * @Return: UserEntity
     * */
    @Test
    void map() throws Exception {

        userMapper = new UserMapperImplements(passwordEncoder);

        UserDTO userDTO = new UserDTO()
                .id(1)
                .username("user0")
                .password("user0")
                .role(UserDTO.RoleEnum.ADMIN)
                .status(UserDTO.StatusEnum.ENABLE)
                .dateCreated(LocalDate.now());

        UserEntity userEntity = userMapper.map(userDTO);

        assertTrue(userEntity instanceof UserEntity);
        assertTrue(userEntity.getUsername() == userDTO.getUsername());
        assertThat(userEntity == null).isFalse();
        assertFalse(userEntity.getUsername() != userDTO.getUsername());
        assertTrue(userEntity.getDateDeleted() == null);

    }

    /**
     * @Function: map
     * @Param: UserRequestDTO
     * @Return: UserEntity
     * */
    @Test
    void testMap() throws Exception {

        userMapper = new UserMapperImplements(passwordEncoder);

        UserRequestDTO userDTO = new UserRequestDTO()
                .username("user0")
                .password("user0")
                .role(UserRequestDTO.RoleEnum.ADMIN)
                .status(UserRequestDTO.StatusEnum.ENABLE)
                .dateCreated(LocalDate.now());

        UserEntity userEntity = userMapper.map(userDTO);

        assertTrue(userEntity instanceof UserEntity);
        assertTrue(userEntity.getUsername() == userDTO.getUsername());
        assertThat(userEntity == null).isFalse();
        assertFalse(userEntity.getUsername() != userDTO.getUsername());
        assertTrue(userEntity.getDateDeleted() == null);
    }

    /**
     * @Function: map
     * @Param: UserEntity
     * @Return: UserDTO
     * */
    @Test
    void testMap1() throws Exception {
        userMapper = new UserMapperImplements(passwordEncoder);

        UserEntity userEntity = UserEntity.builder()
                .username("user0")
                .password("user0")
                .role(Role.ADMIN)
                .status(Status.ENABLE)
                .dateCreated(LocalDate.now())
                .build();

        UserDTO userDTO = userMapper.map(userEntity);

        assertTrue(userDTO instanceof UserDTO);
        assertTrue(userDTO.getUsername() == userEntity.getUsername());
        assertThat(userDTO == null).isFalse();
        assertFalse(userDTO.getUsername() != userEntity.getUsername());
        assertTrue(userDTO.getDateDeleted() == null);
    }
}