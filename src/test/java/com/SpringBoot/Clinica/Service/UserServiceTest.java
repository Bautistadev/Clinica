package com.SpringBoot.Clinica.Service;

import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Repository.UserRepository;
import com.SpringBoot.Clinica.Service.Mapper.UserMapper;
import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapperMock;



    @Autowired
    private UserMapper userMapper;

    /**
     * INICIALIZACION DE TODAS LAS SENTENCIAS DE MOCKITO
     * ANTES DE LAS FUNCIONES DE TESTEO
     * */
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll() {
        UserService userService = new UserService(userRepository,userMapper);

        MockitoAnnotations.openMocks(this);
        Iterable<UserEntity> iterable = this.userRepository.findAll();
        List <UserDTO> expected =  new ArrayList<>();

        iterable.forEach(i ->{
            try {
                expected.add(this.userMapper.map(i));
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        });

        assertEquals(userService.findAll(),expected);


    }

    @Test
    void save() throws Exception {
        UserService userService = new UserService(userRepository,userMapper);
        UserEntity user = UserEntity.builder()
                .username("user 1")
                .password("user 1")
                .role(Role.ADMIN)
                .status(Status.ENABLE)
                .dateCreated(LocalDate.now())
                .build();



        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(user);

        UserRequestDTO simulate = new UserRequestDTO()
                .username("user 1")
                .password("user 1")
                .role(UserRequestDTO.RoleEnum.ADMIN)
                .status(UserRequestDTO.StatusEnum.ENABLE)
                .dateCreated(LocalDate.now());

        UserDTO result = userService.save(simulate);

        Assertions.assertThat(result).isNotNull();
        assertEquals(simulate.getUsername(),result.getUsername());
        assertEquals(simulate.getRole().toString(),result.getRole().toString());


    }


}