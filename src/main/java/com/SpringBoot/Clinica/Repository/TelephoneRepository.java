package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.TelephoneEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class TelephoneRepository implements CrudRepository<TelephoneEntity,Integer> {

    private static Logger LOGGER = LoggerFactory.getLogger(TelephoneRepository.class);

    private JdbcTemplate jdbcTemplate;


    @Value("${spring.db.telephone.insertTelephone}")
    private String SAVE_TELEPHONE;
    @Value("${spring.db.telephone.findById}")
    private String SELECT_BY_ID;
    @Value("${spring.db.telephone.findAll}")
    private String SELECT_ALL;
    @Value("${spring.db.telephone.countTelephone}")
    private String COUNT;
    @Value("${spring.db.telephone.deleteById}")
    private String DELETE;


    public TelephoneRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     *
     * @info:KEY SAVE
     * @param: TelephoneEntity
     * @return TelephoneEntity
     *
     * */
    @Override
    public <S extends TelephoneEntity> S save(S entity) {

        /**
         * SAVE NUMBER
         * */
        if(entity != null) {
            this.jdbcTemplate.update(SAVE_TELEPHONE,
                    entity.getAreaCod(),
                    entity.getNumber());

            LOGGER.trace(String.format("Info: CredentialRepository : save : "+ LocalDate.now().toString() +" : ",entity));
        }else{
            LOGGER.error("Error : TelephoneRepository : save : "+ LocalDate.now().toString());
            throw new NullPointerException("Null entity : save function :  TelephoneRepository");
        }
        return entity;
    }

    @Override
    public <S extends TelephoneEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     *
     * @info: FIND BY ID
     * @param: INTEGER id
     * @return TelephoneEntity
     *
     * */

    @Override
    public Optional<TelephoneEntity> findById(Integer integer) {
        RowMapper<TelephoneEntity> rowMapper = (rs,rowNumber)->{
            TelephoneEntity telephone = TelephoneEntity.builder()
                    .id(rs.getInt("id"))
                    .areaCod(rs.getInt("areaCod"))
                    .number(rs.getInt("number"))
                    .build();

            LOGGER.trace("Info : TelephoneRepository : findById : "+ LocalDate.now());
            return telephone;
        };

        TelephoneEntity telephone = this.jdbcTemplate.queryForObject(SELECT_BY_ID,rowMapper,integer);

        return Optional.of(telephone);
    }

    /**
     *
     * @info: COUNT TELEPHONE
     * @return: Number
     *
     * */
    @Override
    public boolean existsById(Integer integer) {
        return this.jdbcTemplate.queryForObject(COUNT.concat("WHERE id = ?"),(rs,rowNum)->{
            if(rs.getInt("count") == 0)
                return false;
            else
                return true;
        },integer);
    }

    /**
     *
     * @info: FIND ALL
     * @return ITERABLE TelephoneEntity
     *
     * */
    @Override
    public Iterable<TelephoneEntity> findAll() {
        RowMapper<TelephoneEntity> rowMapper = (rs,rowNumber)->{
            TelephoneEntity telephone = TelephoneEntity.builder()
                    .id(rs.getInt("id"))
                    .areaCod(rs.getInt("areaCod"))
                    .number(rs.getInt("number"))
                    .build();

            LOGGER.trace(String.format("Info : TelephoneRepository : findAll : "+ LocalDate.now()+" : ",telephone));
            return telephone;
        };

        return this.jdbcTemplate.query(SELECT_ALL,rowMapper);

    }

    @Override
    public Iterable<TelephoneEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    /**
     *
     * @info: COUNT TELEPHONE
     * @return: Number
     *
     * */
    @Override
    public long count() {
        return this.jdbcTemplate.queryForObject(COUNT,Long.class);
    }

    @Override
    public void deleteById(Integer integer) {
        if(integer == null)
            throw new RuntimeException("Error : TelephoneRepository : deleteById : "+ LocalDateTime.now() +" : NullPoint");
        this.jdbcTemplate.update(DELETE,integer);
        LOGGER.trace("Info : TelephoneRepository : deleteById : "+ LocalDateTime.now() + " : " + integer);
    }

    @Override
    public void delete(TelephoneEntity entity) {
        if(entity == null)
            throw new RuntimeException("Error : TelephoneRepository : delete : "+ LocalDateTime.now() +" : NullPoint");
        deleteById(entity.getId());
        LOGGER.trace("Info : TelephoneRepository : deleteById : "+ LocalDateTime.now() + " : " + entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        integers.forEach(e ->{
            deleteById(e);
        });
    }

    @Override
    public void deleteAll(Iterable<? extends TelephoneEntity> entities) {
        entities.forEach(e ->{
            deleteById(e.getId());
        });
    }

    @Override
    public void deleteAll() {

    }
}
