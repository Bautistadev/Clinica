package com.SpringBoot.Clinica.Configuration;

import com.SpringBoot.Clinica.Service.UserService;
import com.SpringBoot.Clinica.model.UserDTO;
import com.SpringBoot.Clinica.model.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheConfigurationTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserService userService;

    @Test
    void cacheManager() throws Exception {

        ConcurrentHashMap<?,?> cache = (ConcurrentHashMap<?, ?>) this.cacheManager.getCache("users").getNativeCache();

        /**
         * The cache is empty, when then service init
         * */
        assertTrue(cache.isEmpty());

        this.userService.findAll();
        /**
         * The cache is not empty, The cache save the query data
         * */
        assertFalse(cache.isEmpty());

        /**
         * The cache is clear, when a new user is created
         * */
        this.userService.save(new UserRequestDTO()
                .username("userTest")
                .password("userTest")
                .role(UserRequestDTO.RoleEnum.USER)
                .status(UserRequestDTO.StatusEnum.ENABLE)
                .dateCreated(LocalDate.now()));


        assertTrue(cache.isEmpty());

    }
}