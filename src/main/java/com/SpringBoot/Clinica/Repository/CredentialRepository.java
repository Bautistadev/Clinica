package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;

public class CredentialRepository implements CrudRepository<CredentialEntity,Integer> {

    private static Logger LOGGER = LoggerFactory.getLogger(CredentialRepository.class);

    private JdbcTemplate jdbcTemplate;


    @Value("${spring.db.credential.insert}")
    private String SAVE;

    @Value("${spring.db.credential.findAll}")
    private String SELECT_ALL;

    @Value("${spring.db.credential.findById}")
    private String SELECT_BY_ID;

    @Value("${spring.db.credential.deleteById}")
    private String DELETE_BY_ID;

    @Value("${spring.db.credential.countUser}")
    private String COUNT_CREDENTIAL;

    @Value("${spring.db.credential.update}")
    private String UPDATE;

    @Value("${spring.db.credential.findByCredentialNumber}")
    private String FIND_BY_LICENSE_NUMBER;

    public CredentialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     *
     * @info:KEY SAVE
     * @param: CredentialEntity
     * @return CredentialEntity
     *
     * */
    @Override
    public <S extends CredentialEntity> S save(S entity) {

        if(entity != null){
            this.jdbcTemplate.update(SAVE,
                    entity.getName_lastname(),
                    entity.getLicensenumber(),
                    entity.getEspeciality(),
                    entity.getInstitute(),
                    entity.getGraduateDate().toString());

            LOGGER.trace(String.format("Info: CredentialRepository : save : "+ LocalDate.now().toString() +" : ",entity));
        }else{
            LOGGER.error("Error : CredentialRepository : save : "+ LocalDate.now().toString());
            throw new NullPointerException("Null entity : save function :  CredentialRepository");
        }

        return entity;
    }

    @Override
    public <S extends CredentialEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     *
     * @info: FIND BY ID
     * @param: INTEGER id
     * @return CredentialEntity
     *
     * */
    @Override
    public Optional<CredentialEntity> findById(Integer integer) {
        if(integer == null)
            throw  new NullPointerException("Error :  PatientRepository : findById : "+LocalDate.now());

        RowMapper<CredentialEntity> rowMapper = (rs,rowNumber)->{
            CredentialEntity credential = CredentialEntity.builder()
                    .id(rs.getInt("id"))
                    .name_lastname(rs.getString("name_lastname"))
                    .especiality(rs.getString("especiality"))
                    .licensenumber(rs.getString("licenseNumber"))
                    .institute(rs.getString("intitute"))
                    .graduateDate(LocalDate.parse(rs.getString("graduationDate")))
                    .build();

            LOGGER.trace("Info : CredentialRepository : findById : "+ LocalDate.now());
            return credential;
        };

        CredentialEntity credential = this.jdbcTemplate.queryForObject(SELECT_BY_ID,rowMapper,integer);

        return Optional.of(credential);
    }

    /**
     *
     * @info: EXISTS USER BY ID
     * @param: INTEGER id
     * @return BOOLEAN
     *
     * */
    @Override
    public boolean existsById(Integer integer) {
        if(integer == null)
            throw  new NullPointerException("Error :  PatientRepository : findById : "+LocalDate.now());

        return this.jdbcTemplate.queryForObject(COUNT_CREDENTIAL.concat(" WHERE id = ?"),(rs,rowNum) -> {
            if(rs.getInt("count") == 0){
                LOGGER.trace("Info : CredentialRepository : existsById : "+ LocalDate.now() + " : false");
                return false;
            }else{
                LOGGER.trace("Info : CredentialRepository : existsById : "+ LocalDate.now() + " : true");
                return true;
            }
        },integer);
    }


    /**
     *
     * @info: FIND ALL
     * @return ITERABLE CredentialEntity
     *
     * */
    @Override
    public Iterable<CredentialEntity> findAll() {

        RowMapper<CredentialEntity> rowMapper = (rs,rowNumber)->{
            CredentialEntity credential = CredentialEntity.builder()
                    .id(rs.getInt("id"))
                    .name_lastname(rs.getString("name_lastname"))
                    .especiality(rs.getString("especiality"))
                    .licensenumber(rs.getString("licenseNumber"))
                    .institute(rs.getString("intitute"))
                    .graduateDate(LocalDate.parse(rs.getString("graduationDate")))
                    .build();

            LOGGER.trace(String.format("Info : CredentialRepository : findAll : "+ LocalDate.now() + " : "),credential);
            return credential;
        };
        return this.jdbcTemplate.query(SELECT_ALL,rowMapper);
    }

    @Override
    public Iterable<CredentialEntity> findAllById(Iterable<Integer> integers) {
      return null;
    }


    /**
     *
     * @info: COUNT USERS
     * @return NUMBER
     *
     * */
    @Override
    public long count() {
        LOGGER.trace( String.format("Info : CredentialRepository : count : "+ LocalDate.now()));
        return this.jdbcTemplate.queryForObject(COUNT_CREDENTIAL,Long.class);
    }

    /**
     *
     * @info: DELETE BY ID
     * @param: Integer
     *
     * */
    @Override
    public void deleteById(Integer integer) {
        if(integer == null)
            throw  new NullPointerException("Error :  PatientRepository : findById : "+LocalDate.now());

        LOGGER.trace("Info : CredentialRepository : deleteById : "+ LocalDate.now()+ " : "+integer);
        this.jdbcTemplate.update(DELETE_BY_ID,integer);
    }

    /**
     *
     * @info: DELETE BY OBJECT
     * @param: UserEntity
     *
     * */
    @Override
    public void delete(CredentialEntity entity) {
        LOGGER.trace("Info : CredentialRepository : delete : "+ LocalDate.now()+ " : "+entity);
        deleteById(entity.getId());
    }

    /**
     *
     * @info: DELETE ALL BY OBJECT
     * @param: UserEntity
     *
     * */
    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        Iterator i = integers.iterator();

        while (i.hasNext())
            LOGGER.trace("Info : CredentialRepository : deleteAllById: "+ LocalDate.now()+ " : "+i);
            deleteById(Integer.parseInt(i.next().toString()));
    }

    @Override
    public void deleteAll(Iterable<? extends CredentialEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    /**
     *
     * @info: UPDATE
     * @param: entity
     * @return entity
     * */
    public <T extends CredentialEntity> T update(T entity){
        if(entity != null){
            this.jdbcTemplate.update(UPDATE,
                    entity.getName_lastname(),
                    entity.getLicensenumber(),
                    entity.getEspeciality(),
                    entity.getInstitute(),
                    entity.getGraduateDate(),
                    entity.getId());
            LOGGER.trace(String.format("Info : UserRepository : update : "+LocalDate.now()+" : ",entity));

        }else {
            LOGGER.trace("Error : UserRepository : update : "+LocalDate.now());
            throw new NullPointerException("Null entity : UserRepository : update : ");
        }

        return entity;
    }


    public Optional<CredentialEntity> find_by_credential_number(String credentialNumber){
        try {
            RowMapper<CredentialEntity> rowMapper = (rs, rowNumber) -> {
                CredentialEntity credential = CredentialEntity.builder()
                        .id(rs.getInt("id"))
                        .name_lastname(rs.getString("name_lastname"))
                        .especiality(rs.getString("especiality"))
                        .licensenumber(rs.getString("licenseNumber"))
                        .institute(rs.getString("intitute"))
                        .graduateDate(LocalDate.parse(rs.getString("graduationDate")))
                        .build();

                LOGGER.trace("Info : CredentialRepository : findById : " + LocalDate.now());
                return credential;
            };

            CredentialEntity credential = this.jdbcTemplate.queryForObject(FIND_BY_LICENSE_NUMBER, rowMapper, credentialNumber);

            return Optional.of(credential);
        }catch (EmptyResultDataAccessException e){
            LOGGER.error("Error : CredentialRepository class : find_by_credential_number :" + LocalDateTime.now().toString());
            System.out.println(e.getMessage());
            return null;
        }catch (Exception e){
            LOGGER.error("Error : CredentialRepository class : find_by_credential_number :" + LocalDateTime.now().toString());
            System.out.println(e.getMessage());
            return null;
        }

    }
}
