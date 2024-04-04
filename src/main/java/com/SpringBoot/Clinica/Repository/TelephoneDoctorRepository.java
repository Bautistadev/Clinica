package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.DoctorEntity;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 *  KEY: USER ID
 *  VALUE: TELEPHONE NUMBER
 * */

public class TelephoneDoctorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelephoneDoctorRepository.class);

    private JdbcTemplate jdbcTemplate;

    @Value("${spring.db.telephone.insertRelationDoctorTelephone}")
    private String INSERT;
    @Value("${spring.db.telephone.findByDoctorTelephoneId}")
    private String FIND_BY_TELEPHONE_ID;
    @Value("${spring.db.telephone.findByDoctorId}")
    private String FIND_BY_DOCTOR_ID;

    public TelephoneDoctorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveRelation(Integer userId,Integer telephoneId){
        if(userId == null || telephoneId == null)
            throw new RuntimeException("Error : TelephoneUserRepository : saveRelation : "+ LocalDateTime.now() +" : NullPoint");
        this.jdbcTemplate.update(INSERT,telephoneId,userId);
        LOGGER.trace("Info : TelephoneRepository : deleteById : "+ LocalDateTime.now() + " : " + "USER: " + userId + "TELEPHONE: "+telephoneId);
    }
    /**
     * @return: Doctor id
     * */
    public Integer findDoctorByTelephoneId (Integer id){
        RowMapper<Integer> rowMapper =  (rs, rowNum)->{
            Integer i = rs.getInt("Doctor_id");
            return i;
        };

        return this.jdbcTemplate.queryForObject(FIND_BY_TELEPHONE_ID,rowMapper,id);
    }

    /**
     * @return: Telephone id
     * */
    public Integer findTelephoneByDoctorId (Integer id){
        RowMapper<Integer> rowMapper =  (rs, rowNum)->{
            Integer i = rs.getInt("Telephone_id");
            return i;
        };

        return this.jdbcTemplate.queryForObject(FIND_BY_DOCTOR_ID,rowMapper,id);
    }
}
