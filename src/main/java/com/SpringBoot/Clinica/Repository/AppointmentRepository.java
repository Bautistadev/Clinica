package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.AppointmentEntity;
import com.SpringBoot.Clinica.Entity.Enum.AppointmentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.Optional;

public class AppointmentRepository implements CrudRepository<AppointmentEntity,Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentRepository.class);

    private JdbcTemplate jdbcTemplate;

    @Value("${spring.db.appointment.insert}")
    private String SAVE;

    @Value("${spring.db.appointment.findById}")
    private String FIND_BY_ID;

    @Value("${spring.db.appointment.findAll}")
    private String FIND_ALL;

    @Value("${apring.db.appointment.countAppointment}")
    private String COUNT;

    @Value("${spring.db.appointment.deleteById}")
    private String DELETE;

    public AppointmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends AppointmentEntity> S save(S entity) {
        if(entity != null) {
            this.jdbcTemplate.update(SAVE,
                    entity.getAppointmentStatus(),
                    entity.getAppointmentDate(),
                    entity.getDateCreated(),
                    entity.getPatientEntity().getId(),
                    entity.getDoctorEntity().getId());
            LOGGER.trace("Info : AppointmentRepository : SAVE : " + LocalDate.now());
        }else
            throw new NullPointerException("Error : AppointmentRepository : SAVE : "+ LocalDate.now());
        return entity;
    }

    @Override
    public <S extends AppointmentEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<AppointmentEntity> findById(Integer integer) {
        AppointmentEntity appointment = this.jdbcTemplate.queryForObject(FIND_BY_ID,(rs,rowNum)->{

            AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                    .id(rs.getInt("id"))
                    .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                    .appointmentDate(LocalDate.parse(rs.getString("appointment_date")))
                    .build();

            if (rs.getString("appointment_status") == AppointmentStatus.CONFIRMED.getName()){
                appointmentEntity.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            }else if(rs.getString("appointment_status") == AppointmentStatus.COMPLETED.getName()){
                appointmentEntity.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            }else{
                appointmentEntity.setAppointmentStatus(AppointmentStatus.CANCELED);
            }

            if(rs.getString("dateDeleted") != null)
                appointmentEntity.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));

            return appointmentEntity;

        },integer);


        return Optional.of(appointment);
    }

    @Override
    public boolean existsById(Integer integer) {

        return this.jdbcTemplate.queryForObject(COUNT.concat(" WHERE id = ?"),(rs, rowNum) -> {
            if(rs.getInt("COUNT") == 0) {
                LOGGER.trace("Info : AppointmentRepository : existsById : "+ LocalDate.now() + " : FALSE");
                return false;
            }
            LOGGER.trace("Info : AppointmentRepository : existsById : "+ LocalDate.now() + " : TRUE");
            return true;
        }, Long.class);
    }

    @Override
    public Iterable<AppointmentEntity> findAll() {
        RowMapper<AppointmentEntity> rowMapper = (rs,rowNum)->{

            AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                    .id(rs.getInt("id"))
                    .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                    .appointmentDate(LocalDate.parse(rs.getString("appointment_date")))
                    .build();

            if (rs.getString("appointment_status") == AppointmentStatus.CONFIRMED.getName()){
                appointmentEntity.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            }else if(rs.getString("appointment_status") == AppointmentStatus.COMPLETED.getName()){
                appointmentEntity.setAppointmentStatus(AppointmentStatus.CONFIRMED);
            }else{
                appointmentEntity.setAppointmentStatus(AppointmentStatus.CANCELED);
            }

            if(rs.getString("dateDeleted") != null)
                appointmentEntity.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));

            return appointmentEntity;

        };

        return this.jdbcTemplate.query(FIND_ALL,rowMapper);
    }

    @Override
    public Iterable<AppointmentEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return this.jdbcTemplate.queryForObject(COUNT, Long.class);
    }

    @Override
    public void deleteById(Integer integer) {
        if(integer == null)
            throw new NullPointerException("Error :  AppointmentRepository : deleteById : " +LocalDate.now());

        LOGGER.trace("Info : AppointmentRepository :  deleteById : "+LocalDate.now().toString() + " : "+integer);
        this.jdbcTemplate.update(DELETE,integer);
    }

    @Override
    public void delete(AppointmentEntity entity) {
        if(entity == null)
            throw new NullPointerException("Error :  AppointmentRepository : delete : "+LocalDate.now());

        LOGGER.trace("Info :  AppointmentRepository : delete : "+LocalDate.now().toString() + " : " + entity.toString());
        this.deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends AppointmentEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
