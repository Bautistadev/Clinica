package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.Entity.PatientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.Optional;

public class PatientRepository implements CrudRepository<PatientEntity,Integer> {

    private static Logger LOGGER = LoggerFactory.getLogger(CredentialRepository.class);

    private JdbcTemplate jdbcTemplate;


    @Value("${spring.db.patient.insert}")
    private String SAVE;

    @Value("${spring.db.patient.findAll}")
    private String SELECT_ALL;

    @Value("${spring.db.patient.findById}")
    private String SELECT_BY_ID;

    @Value("${spring.db.patient.deleteById}")
    private String DELETE_BY_ID;

    @Value("${spring.db.patient.countUser}")
    private String COUNT;

    @Value("${spring.db.patient.update}")
    private String UPDATE;

    @Value("${spring.db.patient.findByCredentialNumber}")
    private String FIND_BY_LICENSE_NUMBER;

    public PatientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     *
     * @info:KEY SAVE
     * @param: PatientEntity
     * @return PatientEntity
     *
     * */
    @Override
    public <S extends PatientEntity> S save(S entity) {
        if(entity != null)
            throw new NullPointerException("Error :  PatientRepository : SAVE : "+ LocalDate.now());

        this.jdbcTemplate.update(SAVE,
                entity.getName(),
                entity.getLastname(),
                entity.getDni(),
                entity.getBirthdate(),
                entity.getGender(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getDateCreated());

        return entity;
    }

    @Override
    public <S extends PatientEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     *
     * @info: FIND BY ID
     * @param: INTEGER id
     * @return PatientEntity
     *
     * */
    @Override
    public Optional<PatientEntity> findById(Integer integer) {

        if(integer == null)
            throw  new NullPointerException("Error :  PatientRepository : findById : "+LocalDate.now());

        RowMapper<PatientEntity> rowMapper = (rs, rowNumber)->{
            PatientEntity credential = PatientEntity.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .address(rs.getString("address"))
                    .dni(rs.getInt("dni"))
                    .birthdate(LocalDate.parse(rs.getString("dateCreated")))
                    .email(rs.getString("email"))
                    .gender(rs.getString("gender"))
                    .build();

            if(rs.getString("dateDeleted") == null)
                credential.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));

            LOGGER.trace("Info : PatientRepository : findById : "+ LocalDate.now());
            return credential;
        };


        return Optional.of(this.jdbcTemplate.queryForObject(SELECT_BY_ID,rowMapper,integer));
    }

    /**
     *
     * @info: EXISTS PATIENT BY ID
     * @param: INTEGER id
     * @return BOOLEAN
     *
     * */
    @Override
    public boolean existsById(Integer integer) {
        return this.jdbcTemplate.queryForObject(COUNT.concat(" WHERE id =? "),(rs,rowNum)->{
            if(rs.getInt("count") == 0) {
                LOGGER.trace("Info : PatientRepository : existsById : "+LocalDate.now() + " : False");
                return false;
            }

            LOGGER.trace("Info : PatientRepository : existsById : "+LocalDate.now() + " : True");
            return true;
        },integer);
    }

    /**
     *
     * @info: FIND ALL
     * @return ITERABLE PatientEntity
     *
     * */
    @Override
    public Iterable<PatientEntity> findAll() {
        RowMapper<PatientEntity> rowMapper = (rs, rowNumber)->{
            PatientEntity credential = PatientEntity.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .address(rs.getString("address"))
                    .dni(rs.getInt("dni"))
                    .birthdate(LocalDate.parse(rs.getString("dateCreated")))
                    .email(rs.getString("email"))
                    .gender(rs.getString("gender"))
                    .build();

            if(rs.getString("dateDeleted") == null)
                credential.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));

            LOGGER.trace("Info : PatientRepository : findById : "+ LocalDate.now());
            return credential;
        };
        return this.jdbcTemplate.query(SELECT_ALL,rowMapper);
    }

    @Override
    public Iterable<PatientEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    /**
     *
     * @info: COUNT PATIENT
     * @return NUMBER
     *
     * */
    @Override
    public long count() {
        return this.jdbcTemplate.queryForObject(COUNT,Long.class);
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

        this.jdbcTemplate.update(DELETE_BY_ID,integer);
    }

    @Override
    public void delete(PatientEntity entity) {
        if(entity == null)
            throw  new NullPointerException("Error :  PatientRepository : findById : "+LocalDate.now());

        this.deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends PatientEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
