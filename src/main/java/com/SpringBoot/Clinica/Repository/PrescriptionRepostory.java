package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.MedicineEntity;
import com.SpringBoot.Clinica.Entity.PrescriptionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.Optional;

public class PrescriptionRepostory implements CrudRepository<PrescriptionEntity,Integer> {

   private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionRepostory.class);

   private JdbcTemplate jdbcTemplate;

   @Value("${spring.db.prescription.insert}")
   private String SAVE;

   @Value("${spring.db.prescription.findById}")
   private String SELECT_PRESCRIPTION_BY_ID;

   @Value("${spring.db.prescription.findAll}")
   private String SELECT_ALL;

   @Value("${spring.db.prescription.countPatient}")
   private String COUNT;

   @Value("${spring.db.prescription.deleteById}")
   private String DELETE_BY_ID;

    @Override
    public <S extends PrescriptionEntity> S save(S entity) {
        if(entity !=null){
            this.jdbcTemplate.update(SAVE,
                    entity.getPrescription_date(),
                    entity.getDose(),
                    entity.getInstruction(),
                    entity.getMedicineEntity().getId());
            LOGGER.trace(String.format("Info : PrescriptionRepository : SAVE : "+ LocalDate.now() + " : ",entity));
        }else{
            LOGGER.error("Error :  PrescriptionRepository : save :" + LocalDate.now());
            throw new NullPointerException("Error :  PrescriptionRepository : save :" + LocalDate.now());
        }
        return entity;
    }

    @Override
    public <S extends PrescriptionEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<PrescriptionEntity> findById(Integer integer) {

        PrescriptionEntity prescription = this.jdbcTemplate.queryForObject(SELECT_PRESCRIPTION_BY_ID,(rs,rowNum)->{
            PrescriptionEntity prescriptionEntity = PrescriptionEntity.builder()
                    .id(rs.getInt("id"))
                    .dose(rs.getString("dose"))
                    .instruction(rs.getString("instruction"))
                    .prescription_date(LocalDate.parse(rs.getString("prescription_date")))
                    .medicineEntity( MedicineEntity.builder()
                            .id(rs.getInt("Medicine_id"))
                            .build())
                    .build();

            LOGGER.trace("Info : PrescriptionRepository : findbyid : "+LocalDate.now());
            return prescriptionEntity;
        },integer);

        return Optional.of(prescription);
    }

    @Override
    public boolean existsById(Integer integer) {

        if(this.jdbcTemplate.queryForObject(COUNT+"WHERE id=?",Long.class) == 0){
            LOGGER.trace("Error : PrescriptionRepository : existsById : "+LocalDate.now());
            return false;
        }else{
            LOGGER.trace("Info : PrescriptionRepository : existsById : "+LocalDate.now());
            return true;
        }
    }

    @Override
    public Iterable<PrescriptionEntity> findAll() {
        RowMapper<PrescriptionEntity> prescription = (rs,rowNum)->{
            PrescriptionEntity prescriptionEntity = PrescriptionEntity.builder()
                    .id(rs.getInt("id"))
                    .dose(rs.getString("dose"))
                    .instruction(rs.getString("instruction"))
                    .prescription_date(LocalDate.parse(rs.getString("prescription_date")))
                    .medicineEntity( MedicineEntity.builder()
                            .id(rs.getInt("Medicine_id"))
                            .build())
                    .build();

            return prescriptionEntity;
        };

        return this.jdbcTemplate.query(SELECT_ALL,prescription);
    }

    @Override
    public Iterable<PrescriptionEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return this.jdbcTemplate.queryForObject(COUNT,Long.class);
    }

    @Override
    public void deleteById(Integer integer) {
        LOGGER.trace("Info : PrescriptionRepository : deleteById : "+LocalDate.now() + " : ID:" + integer);
        this.jdbcTemplate.update(DELETE_BY_ID,integer);
    }

    @Override
    public void delete(PrescriptionEntity entity) {
        LOGGER.trace("Info : PrescriptionRepository : deleteById : "+LocalDate.now() + " : "+entity);
        this.jdbcTemplate.update(DELETE_BY_ID,entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends PrescriptionEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
