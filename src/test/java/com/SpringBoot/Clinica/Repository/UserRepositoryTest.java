package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Repository.UserRepository;
import org.apache.catalina.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {

        //GIVEN
        UserEntity user = UserEntity.builder()
                .username("BAUTISTAa")
                .password("BAUTISTAa")
                .role(Role.ADMIN)
                .status(Status.ENABLE)
                .dateCreated(LocalDate.now())
                .build();

        UserEntity userSave = this.userRepository.save(user);

        //TEST
        assertThat(userSave).isNotNull();
        assertThat(userSave.getUsername()).isEqualTo(user.getUsername());
        assertThat(userSave instanceof  UserEntity).isTrue();
        assertNull(userSave.getId(),"El id deberia ser nulo");
        assertNotNull(userSave.getUsername(),"El nombre de usuario no debaira ser nulo");

    }

    @Test
    void findById() {

        //GIVEN
        Optional<UserEntity> user = this.userRepository.findById(1);

        //TEST
        assertNotNull(user);
        assertThat(user.get() instanceof UserEntity).isTrue();
        assertNotNull(user.get().getId());
    }

    @Test
    void existsById() {

        //GIVEN
        Boolean isExits = this.userRepository.existsById(1);
        Boolean isNotExits = this.userRepository.existsById(10000);

        //TEST
        assertTrue(isExits);
        assertFalse(isNotExits);

    }

    @Test
    void findAll() {

        //GIVEN
        Iterable<UserEntity> userEntitiesIterable = this.userRepository.findAll();
        List<UserEntity> UserList = new ArrayList<>();

        userEntitiesIterable.forEach(e ->{
            UserList.add(e);
        });

        //TEST
        assertThat(userEntitiesIterable instanceof Iterable<UserEntity>).isTrue();
        assertThat(UserList.get(1) != null).isTrue();
        assertThat(UserList.isEmpty()).isFalse();

    }

    @Test
    void count() {
        Long cantUser = this.userRepository.count();
        assertThat(cantUser instanceof Long).isTrue();
    }

    @Test
    void deleteById() {

        UserEntity user = UserEntity.builder()
                .username("BAUTISTA2")
                .password("BAUTISTA2")
                .role(Role.USER)
                .status(Status.ENABLE)
                .dateCreated(LocalDate.now())
                .build();


        this.userRepository.save(user);

        UserEntity userDB = this.userRepository.findByUsername(user.getUsername()).get();

        this.userRepository.deleteById(userDB.getId());

        assertNull(this.userRepository.findByUsername(user.getUsername()));

   }

    @Test
    void delete() {
    }
}