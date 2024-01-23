package com.SpringBoot.Clinica.Repository;


import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import com.SpringBoot.Clinica.Entity.UserEntity;

import com.SpringBoot.Clinica.Exception.DataException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;


public class UserRepository implements CrudRepository<UserEntity, Integer> {


    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;


    @Value("${spring.db.user.insert}")
    private static String SAVE;
    @Value("${spring.db.user.findAll}")
    private static String SELECT_ALL;
    @Value("${spring.db.user.findById}")
    private static String SELECT_BY_ID;
    @Value("${spring.db.user.delete}")
    private static String DELETE;
    @Value("${spring.db.user.countUser}")
    private static String COUNT_USERS;

    public UserRepository(JdbcTemplate jdbcTemplate,PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     *
     * @info: SAVE USER
     * @param: UserEntity object
     * @return: UserEntity object
     *
     * */
    @Override
    public <S extends UserEntity> S save(S entity){

        if(entity != null) {
            this.jdbcTemplate.update(SAVE,
                    entity.getUsername(),
                    entity.getPassword(),
                    entity.getRole(),
                    entity.getStatus(),
                    entity.getDateCreated()
            );
        }else{
            throw new NullPointerException("Null entity : save function : UserRepository");
        }

        return entity;
    }

    @Override
    public <S extends UserEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<UserEntity> findById(Integer integer) {
        UserEntity user = this.jdbcTemplate.queryForObject(SELECT_BY_ID,(rs, rowNum)->{
            UserEntity userEntity = UserEntity.builder()
                    .id(rs.getInt("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                    .build();

            /**DATE DELETED*/
            if (rs.getString("dateDeleted") != null){
                userEntity.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));
            }else{
                userEntity.setDateDeleted(null);
            }

            /**ROLE*/
            if (rs.getString("role").equals(Role.ADMIN.getRoleName()))
                userEntity.setRole(Role.ADMIN);
            else
                userEntity.setRole(Role.USER);

            /**STATUS*/
            if (rs.getString("status").equals(Status.ENABLE.getStatusName()))
                userEntity.setStatus(Status.ENABLE);
            else if (rs.getString("status").equals(Status.DISABLE.getStatusName()))
                userEntity.setStatus(Status.DISABLE);
            else
                userEntity.setStatus(Status.SUSPENDED);

            return  userEntity;

        },integer);

        return Optional.of(user);
    }

    @Override
    public boolean existsById(Integer integer) {
        return this.jdbcTemplate.queryForObject(COUNT_USERS.concat(" WHERE id = "),(rs,rowNum) -> {

            if(rs.getInt("count") == 0)
                return false;
            else
                return true;

        },integer);
    }


    /**
     *
     * @info: Find all users
     * @return: UserEntity iterable object
     *
     * */
    @Override
    public Iterable<UserEntity> findAll() {
        RowMapper<UserEntity> rowMapper = (rs,rowNum)->{
            UserEntity userEntity = UserEntity.builder()
                    .id(rs.getInt("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                    .build();

            /**DATE DELETED*/
            if (rs.getString("dateDeleted") != null){
                userEntity.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));
            }else{
                userEntity.setDateDeleted(null);
            }

            /**ROLE*/
            if (rs.getString("role").equals(Role.ADMIN.getRoleName()))
                userEntity.setRole(Role.ADMIN);
            else
                userEntity.setRole(Role.USER);

            /**STATUS*/
            if (rs.getString("status").equals(Status.ENABLE.getStatusName()))
                userEntity.setStatus(Status.ENABLE);
            else if (rs.getString("status").equals(Status.DISABLE.getStatusName()))
                userEntity.setStatus(Status.DISABLE);
            else
                userEntity.setStatus(Status.SUSPENDED);

            return  userEntity;

        };

        return this.jdbcTemplate.query(SELECT_ALL,rowMapper);
    }

    @Override
    public Iterable<UserEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return this.jdbcTemplate.query(COUNT_USERS,(rs -> {
            return rs.getLong("count");
        }));
    }

    @Override
    public void deleteById(Integer integer) {
        this.jdbcTemplate.update(DELETE,integer);
    }

    @Override
    public void delete(UserEntity entity) {
        this.jdbcTemplate.update(DELETE,entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        Iterator iterator = integers.iterator();

        while (iterator.hasNext())
            this.jdbcTemplate.update(DELETE,iterator.next());
    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {
        Iterator iterator = entities.iterator();

        while (iterator.hasNext()) {
            UserEntity user = (UserEntity) iterator.next();
            this.jdbcTemplate.update(DELETE, user.getId());
        }
    }

    @Override
    public void deleteAll() {

    }
}
