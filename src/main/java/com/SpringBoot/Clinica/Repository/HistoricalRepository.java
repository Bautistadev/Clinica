package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.AppointmentEntity;
import com.SpringBoot.Clinica.Entity.HistoricalEntity;
import com.SpringBoot.Clinica.Entity.PatientEntity;
import com.SpringBoot.Clinica.Entity.PrescriptionEntity;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.Optional;

public class HistoricalRepository implements CrudRepository<HistoricalEntity,Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalRepository.class);

    @Value("${spring.db.historical.insertHistorical}")
    private String SAVE;

    @Value("${spring.db.historical.findPatientId}")
    private String FIND_BY_ID;

    @Value("${spring.db.historical.findByAppointmentId}")
    private String FIND_BY_PATIENT_ID;

    @Value("${spring.db.historical.findById}")
    private String FIND_BY_APPOINTMENT_ID;

    @Value("${spring.db.historical.findAll}")
    private String FIND_ALL;

    private JdbcTemplate jdbcTemplate;

    public HistoricalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends HistoricalEntity> S save(S entity) {

        if(entity != null)
            throw new IllegalArgumentException("Error : HistoricalRepository : save : "+ LocalDate.now());

        this.jdbcTemplate.update(SAVE,
                entity.getDiagnosis(),
                entity.getTratment(),
                entity.getRecomended(),
                entity.getResult(),
                entity.getAppointmentEntity().getId(),
                entity.getPatientEntity().getId(),
                entity.getPrescriptionEntity().getId()
        );

        LOGGER.trace("Error : HistoricalRepository : save : "+ LocalDate.now());
        return entity;
    }

    @Override
    public <S extends HistoricalEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<HistoricalEntity> findById(Integer integer) {
        RowMapper<HistoricalEntity> historicalEntityRowMapper = (rs,rowNum)->{
            HistoricalEntity historical =  HistoricalEntity.builder()
                    .id(rs.getInt("id"))
                    .diagnosis(rs.getString("diagnosis"))
                    .recomended(rs.getString("recommends"))
                    .result(rs.getString("result"))
                    .appointmentEntity(
                            AppointmentEntity.builder().id(rs.getInt("Appointment_id")).build())
                    .patientEntity(
                            PatientEntity.builder().id(rs.getInt("Patient_id")).build())
                    .prescriptionEntity(
                            PrescriptionEntity.builder().id(rs.getInt("Prescription_id")).build())
                    .build();
            return historical;
        };
        HistoricalEntity response =  this.jdbcTemplate.queryForObject(FIND_BY_ID,historicalEntityRowMapper,integer);
        return Optional.of(response);
    }

    public Iterable<HistoricalEntity> findByPatientId(Integer integer){
        RowMapper<HistoricalEntity> historicalEntityRowMapper = (rs,rowNum)->{
            HistoricalEntity historical =  HistoricalEntity.builder()
                    .id(rs.getInt("id"))
                    .diagnosis(rs.getString("diagnosis"))
                    .recomended(rs.getString("recommends"))
                    .result(rs.getString("result"))
                    .appointmentEntity(
                            AppointmentEntity.builder().id(rs.getInt("Appointment_id")).build())
                    .patientEntity(
                            PatientEntity.builder().id(rs.getInt("Patient_id")).build())
                    .prescriptionEntity(
                            PrescriptionEntity.builder().id(rs.getInt("Prescription_id")).build())
                    .build();
            return historical;
        };
        Iterable<HistoricalEntity> response =  this.jdbcTemplate.query(FIND_BY_PATIENT_ID,historicalEntityRowMapper,integer);
        return response;
    }

    public Iterable<HistoricalEntity> findByAppointmentId(Integer integer){
        RowMapper<HistoricalEntity> historicalEntityRowMapper = (rs,rowNum)->{
            HistoricalEntity historical =  HistoricalEntity.builder()
                    .id(rs.getInt("id"))
                    .diagnosis(rs.getString("diagnosis"))
                    .recomended(rs.getString("recommends"))
                    .result(rs.getString("result"))
                    .appointmentEntity(
                            AppointmentEntity.builder().id(rs.getInt("Appointment_id")).build())
                    .patientEntity(
                            PatientEntity.builder().id(rs.getInt("Patient_id")).build())
                    .prescriptionEntity(
                            PrescriptionEntity.builder().id(rs.getInt("Prescription_id")).build())
                    .build();
            return historical;
        };
        Iterable<HistoricalEntity> response =  this.jdbcTemplate.query(FIND_BY_APPOINTMENT_ID,historicalEntityRowMapper,integer);
        return response;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<HistoricalEntity> findAll() {
        RowMapper<HistoricalEntity> historicalEntityRowMapper = (rs,rowNum)->{
            HistoricalEntity historical =  HistoricalEntity.builder()
                    .id(rs.getInt("id"))
                    .diagnosis(rs.getString("diagnosis"))
                    .recomended(rs.getString("recommends"))
                    .result(rs.getString("result"))
                    .appointmentEntity(
                            AppointmentEntity.builder().id(rs.getInt("Appointment_id")).build())
                    .patientEntity(
                            PatientEntity.builder().id(rs.getInt("Patient_id")).build())
                    .prescriptionEntity(
                            PrescriptionEntity.builder().id(rs.getInt("Prescription_id")).build())
                    .build();
            return historical;
        };
        Iterable<HistoricalEntity> response =  this.jdbcTemplate.query(FIND_ALL,historicalEntityRowMapper);
        return response;
    }

    @Override
    public Iterable<HistoricalEntity> findAllById(Iterable<Integer> integers) {
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
    public void delete(HistoricalEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends HistoricalEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

}
