package com.SpringBoot.Clinica.Repository;


import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import com.SpringBoot.Clinica.Entity.UserEntity;

import com.SpringBoot.Clinica.model.UserDTO;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.Optional;

@Repository
public class UserRepository implements CrudRepository<UserEntity, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private JdbcTemplate jdbcTemplate;

   // @Value("${spring.db.user.insert}")
    private  String SAVE = "INSERT INTO User(username,password,role,status,dateCreated) VALUES(?,?,?,?,?)";
    @Value("${spring.db.user.findAll}")
    private String SELECT_ALL;
    @Value("${spring.db.user.findById}")
    private String SELECT_BY_ID;
    @Value("${spring.db.user.findByUsername}")
    private String SELECT_BY_USERNAME;
    @Value("${spring.db.user.delete}")
    private String DELETE;
    @Value("${spring.db.user.countUser}")
    private String COUNT_USERS;
    @Value("${spring.db.user.update}")
    private String UPDATE;
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

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
                    entity.getRole().getRoleName(),
                    entity.getStatus().getStatusName(),
                    entity.getDateCreated()

            );
            LOGGER.trace(String.format("Info : UserRepository : save : " + LocalDateTime.now().toString() + " : ", entity));
        }else{
            LOGGER.error("Error : UserRepository class : save :"+ LocalDateTime.now().toString());
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

            LOGGER.trace("Info : UserRepository class : findById : "+LocalDateTime.now().toString());
            return  userEntity;

        },integer);

        return Optional.of(user);
    }

    @Override
    public boolean existsById(Integer integer) {
        return this.jdbcTemplate.queryForObject(COUNT_USERS.concat(" WHERE id = ? "),(rs,rowNum) -> {

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

            LOGGER.trace("Info : UserRepository class : findAll : "+LocalDateTime.now().toString());
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

        return this.jdbcTemplate.queryForObject(COUNT_USERS, Long.class);
    }

    @Override
    public void deleteById(Integer integer) {

        this.jdbcTemplate.update(DELETE, integer);
        LOGGER.trace("Info : UserRepository class : deleteById : "+LocalDateTime.now().toString() +" : ",integer);

    }

    @Override
    public void delete(UserEntity entity) {
        try {
            this.jdbcTemplate.update(DELETE, entity.getId());
            LOGGER.trace("Info : UserRepository class : delete : "+LocalDateTime.now().toString() +" : ",entity);
        }catch (Exception e){
            LOGGER.error("Error : UserRepository class : delete : "+LocalDateTime.now().toString());
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        Iterator iterator = integers.iterator();

        while (iterator.hasNext()) {
            UserEntity user = (UserEntity) iterator.next();
            this.jdbcTemplate.update(DELETE, user);
            LOGGER.trace("Info : UserRepository class : deleteAllById : "+LocalDateTime.now().toString() +" : ",user);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {
        Iterator iterator = entities.iterator();

        while (iterator.hasNext()) {
            UserEntity user = (UserEntity) iterator.next();
            LOGGER.trace("Info : UserRepository class : deleteAll : "+LocalDateTime.now().toString() +" : ",user);
            this.jdbcTemplate.update(DELETE, user.getId());
        }
    }

    @Override
    public void deleteAll() {

    }

    public Optional<UserEntity> findByUsername(String username){
        try {
            UserEntity user = this.jdbcTemplate.queryForObject(SELECT_BY_USERNAME, (rs, rowNum) -> {
                UserEntity userEntity = UserEntity.builder()
                        .id(rs.getInt("id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                        .build();

                /**DATE DELETED*/
                if (rs.getString("dateDeleted") != null) {
                    userEntity.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));
                } else {
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

                LOGGER.trace(String.format("Info : UserRepository class : findByUsername : " +LocalDateTime.now().toString()+" : ", userEntity));
                return userEntity;

            }, username);

            return Optional.of(user);

        }catch (EmptyResultDataAccessException e){
            LOGGER.error("Error : UserRepository class : findByUsername :" + LocalDateTime.now().toString());
            System.out.println(e.getMessage());
            return null;
        }catch (Exception e){
            LOGGER.error("Error : UserRepository class : findByUsername :" + LocalDateTime.now().toString());
            System.out.println(e.getMessage());
            return null;
        }

    }


    public < T extends UserEntity >T update(T entity) {
        if(entity!= null) {
            this.jdbcTemplate.update(UPDATE,
                    entity.getUsername(),
                    entity.getPassword(),
                    entity.getRole().getRoleName(),
                    entity.getStatus().getStatusName(),
                    entity.getId()

            );
            LOGGER.trace(String.format("Info : UserRepository : save : " + LocalDateTime.now().toString() + " : ", entity));
        }else{
            LOGGER.error("Error : UserRepository class : save :"+ LocalDateTime.now().toString());
            throw new NullPointerException("Null entity : save function : UserRepository");
        }

        return entity;

    }
}
