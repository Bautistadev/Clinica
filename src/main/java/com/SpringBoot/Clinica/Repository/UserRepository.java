package com.SpringBoot.Clinica.Repository;


import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Excepcion.DataException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.sql.SQLException;
import java.util.Optional;


public class UserRepository implements CrudRepository<UserEntity, Integer> {


    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;


    @Value("${spring.db.insert}")
    private static String SAVE;

    public UserRepository(JdbcTemplate jdbcTemplate,PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public <S extends UserEntity> S save(S entity){


        this.jdbcTemplate.update(SAVE,
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole(),
                entity.getStatus(),
                entity.getDateCreated()
                );

        return entity;
    }

    @Override
    public <S extends UserEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<UserEntity> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<UserEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<UserEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(UserEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
