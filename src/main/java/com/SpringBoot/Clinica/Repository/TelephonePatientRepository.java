package com.SpringBoot.Clinica.Repository;

import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;


public class TelephonePatientRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelephonePatientRepository.class);

    private JdbcTemplate jdbcTemplate;

    @Value("${spring.db.telephone.insertRelationPatientTelephone}")
    private String INSERT;

    @Value("${spring.db.telephone.findByDoctorPatientId}")
    private String FIND_BY_TELEPHONE_ID;

    @Value("${spring.db.telephone.findByPatientId}")
    private String FIND_BY_DOCTOR_ID;

    public TelephonePatientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return: SAVE RELATION
     * */
    public void saveRelation(Integer patientId, Integer telephoneId){
        if(patientId == null || telephoneId == null)
            throw new RuntimeException("Error : TelephonePatientRepository : saveRelation : "+ LocalDate.now());
        this.jdbcTemplate.update(INSERT,telephoneId,patientId);
        LOGGER.trace("Info : TelephonePatientRepository : saveRelation : "+LocalDate.now());
    }

    /**
     * @return : Patient id
     * */
    public Integer findDoctorByTelephoneId(Integer id){
        RowMapper<Integer> rowMapper = (rs,rowNum)->{
            Integer i = rs.getInt("Patient_id");
            return i;
        };

        return this.jdbcTemplate.queryForObject(FIND_BY_TELEPHONE_ID,rowMapper,id);
    }

    /**
     * @return: Telephone id
     * */
    public Integer findTelephoneByPatientId(Integer id){
        RowMapper<Integer> rowMapper =(rs,rowNum)->{
            Integer i =  rs.getInt("Telephone_id");
            return i;
        };

        return this.jdbcTemplate.queryForObject(FIND_BY_DOCTOR_ID,rowMapper,id);
    }
}
