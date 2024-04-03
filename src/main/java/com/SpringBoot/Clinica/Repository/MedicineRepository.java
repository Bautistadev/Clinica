package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.MedicineEntity;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class MedicineRepository implements CrudRepository<MedicineEntity,Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicineRepository.class);

    private JdbcTemplate jdbcTemplate;

    @Value("${spring.db.medicine.findAll}")
    private String FIND_ALL;
    @Value("${spring.db.medicine.findById}")
    private String FIND_BY_ID;
    @Value("${spring.db.medicine.insert}")
    private String INSERT;
    @Value("${spring.db.medicine.deleteById}")
    private String DELETE_BY_ID;
    @Value("${spring.db.medicine.countMedicine}")
    private String COUNT_MEDICINE;
    @Value("${spring.db.medicine.update}")
    private String UPDATE;

    public MedicineRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @operation: SAVE
     * @param: MedicineEntity
     * @return: MedicineEntity
     * */
    @Override
    public <S extends MedicineEntity> S save(S entity) {
        if(entity == null) {
            LOGGER.error("Error : MedicineRepository : save : " + LocalDate.now());
            throw new RuntimeException("Error : MedicineRepository : save : " + LocalDateTime.now());
        }
        this.jdbcTemplate.update(INSERT,
                entity.getName(),
                entity.getDescription_medicine(),
                entity.getInstruction(),
                entity.getDateCreated());
        return entity;
    }

    @Override
    public <S extends MedicineEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     * @operation: FIND BY ID
     * @param: Integer id
     * @return: MedicineEntity
     * */
    @Override
    public Optional<MedicineEntity> findById(Integer integer) {
        if(integer == null || integer instanceof Integer) {
            LOGGER.error("Info : MedicineRepository : findById : " + LocalDate.now());
            throw new RuntimeException("Null entity : findById :  MedicineRepository");
        }

        RowMapper<MedicineEntity> rowMapper = (rs,colNum)->{
          MedicineEntity medicineEntity = MedicineEntity.builder()
                  .id(rs.getInt("id"))
                  .name(rs.getString("name"))
                  .instruction(rs.getString("instruction"))
                  .description_medicine(rs.getString("description_Medicine"))
                  .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                  .build();

          LOGGER.trace("Info : MedicineRepository : findById : "+ LocalDate.now());
          return medicineEntity;
        };

        MedicineEntity medicineEntity = this.jdbcTemplate.queryForObject(FIND_BY_ID,rowMapper,integer);

        return Optional.of(medicineEntity);
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    /**
     * @operation: FIND ALL
     * @return: Iterable MedicineEntity
     * */
    @Override
    public Iterable<MedicineEntity> findAll() {

        RowMapper<MedicineEntity> rowMapper = (rs,colNum)->{
            MedicineEntity medicineEntity = MedicineEntity.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .instruction(rs.getString("instruction"))
                    .description_medicine(rs.getString("description_Medicine"))
                    .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                    .build();

            LOGGER.trace("Info : MedicineRepository : findById : "+ LocalDate.now());
            return medicineEntity;
        };

       return this.jdbcTemplate.query(FIND_ALL,rowMapper);


    }

    @Override
    public Iterable<MedicineEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        LOGGER.trace("Info : MedicineRepository : count : "+ LocalDate.now());
        return this.jdbcTemplate.queryForObject(COUNT_MEDICINE, Long.class);
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(MedicineEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends MedicineEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
