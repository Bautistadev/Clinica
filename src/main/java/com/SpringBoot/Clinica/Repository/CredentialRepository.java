package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
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

    public CredentialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



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
            throw new NullPointerException("Null entity : save function :  DoctorRepository");
        }

        return entity;
    }

    @Override
    public <S extends CredentialEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<CredentialEntity> findById(Integer integer) {
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

    @Override
    public boolean existsById(Integer integer) {
        return this.jdbcTemplate.queryForObject(COUNT_CREDENTIAL.concat("WHERE id = ?"),(rs,rowNum) -> {
            if(rs.getInt("count") == 0){
                return false;
            }else{
                return true;
            }
        },integer);
    }

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

            LOGGER.trace("Info : CredentialRepository : findAll : "+ LocalDate.now());
            return credential;
        };
        return this.jdbcTemplate.query(SELECT_ALL,rowMapper);
    }

    @Override
    public Iterable<CredentialEntity> findAllById(Iterable<Integer> integers) {
      return null;
    }

    @Override
    public long count() {
        return this.jdbcTemplate.queryForObject(COUNT_CREDENTIAL,Long.class);
    }

    @Override
    public void deleteById(Integer integer) {
        this.jdbcTemplate.update(DELETE_BY_ID,integer);
    }

    @Override
    public void delete(CredentialEntity entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        Iterator i = integers.iterator();

        while (i.hasNext())
            deleteById(Integer.parseInt(i.next().toString()));
    }

    @Override
    public void deleteAll(Iterable<? extends CredentialEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

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
}
