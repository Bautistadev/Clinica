package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.Entity.DoctorEntity;
import com.SpringBoot.Clinica.Entity.UserEntity;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class DoctorRepository implements CrudRepository<DoctorEntity,Integer> {

    private static Logger LOGGER = LoggerFactory.getLogger(DoctorRepository.class);

    private JdbcTemplate jdbcTemplate;

    @Value("${spring.db.doctor.insert}")
    private String SAVE;
    @Value("${spring.db.doctor.findAll}")
    private String SELECT_ALL;
    @Value("${spring.db.doctor.findById}")
    private String SELECT_BY_ID;
    @Value("${spring.db.doctor.deleteById}")
    private String DELETE_BY_ID;
    @Value("${spring.db.doctor.countDoctor}")
    private String COUNT_DOCTOR;
    @Value("${spring.db.doctor.update}")
    private String UPDATE;

    public DoctorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @operation: save
     * @param: DoctorEntity
     * @return: DoctorEntity
     * */
    @Override
    public <S extends DoctorEntity> S save(S entity) {
        if(entity != null){
            this.jdbcTemplate.update(SAVE,
                    entity.getName(),
                    entity.getLastname(),
                    entity.getDni(),
                    entity.getEmail(),
                    entity.getDateCreated(),
                    entity.getUser().getId(),
                    entity.getCredential().getId());

            LOGGER.trace(String.format("Info : DoctorRepository : save : "+LocalDate.now()+" : ",entity));
        }else{
            LOGGER.trace("Error : DoctorRepository : save : "+LocalDate.now());
            throw new NullPointerException("Error : DoctorRepository : save : "+LocalDate.now());
        }
        return entity;
    }

    @Override
    public <S extends DoctorEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     * @operation: find by id
     * @param: Integer
     * @return: Optional --> DoctorEntity
     * */
    @Override
    public Optional<DoctorEntity> findById(Integer integer) {

        DoctorEntity doctor = this.jdbcTemplate.queryForObject(SELECT_BY_ID,(rs,rowNum)->{

            DoctorEntity doctorEntity = DoctorEntity.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .lastname(rs.getString("lastname"))
                    .dni(rs.getInt("dni"))
                    .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                    .email(rs.getString("email"))
                    .build();

            if(rs.getString("dateDeleted") != null)
                doctorEntity.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));
            else
                doctorEntity.setDateDeleted(null);

            doctorEntity.setUser(UserEntity.builder()
                    .username(rs.getString("username"))
                    .build());
            doctorEntity.setCredential(CredentialEntity.builder()
                    .id(rs.getInt("idCredential"))
                    .especiality(rs.getString("especiality"))
                    .licensenumber(rs.getString("licenseNumber"))
                    .build());

            return doctorEntity;
        },integer);

        return Optional.of(doctor);
    }

    /**
     * @operation: exists by id
     * @param: Integer
     * @return: Boolean
     * */
    @Override
    public boolean existsById(Integer integer) {
        if(this.count() == 0)
            return false;
        else
            return true;
    }

    /**
     * @operation: find all doctos
     * @return: Iterable --> DoctorEntity
     * */
    @Override
    public Iterable<DoctorEntity> findAll() {

        RowMapper<DoctorEntity> rowMapper = (rs,rowNum)->{

            DoctorEntity doctorEntity = DoctorEntity.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .lastname(rs.getString("lastname"))
                    .dni(rs.getInt("dni"))
                    .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                    .email(rs.getString("email"))
                    .build();

            if(rs.getString("dateDeleted") != null)
                doctorEntity.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));
            else
                doctorEntity.setDateDeleted(null);

            doctorEntity.setUser(UserEntity.builder()
                            .username(rs.getString("username"))
                            .build());
            doctorEntity.setCredential(CredentialEntity.builder()
                            .id(rs.getInt("idCredential"))
                            .especiality(rs.getString("especiality"))
                            .licensenumber(rs.getString("licenseNumber"))
                            .build());

            LOGGER.trace("Info: DoctorRepository : findAll : "+ LocalDate.now().toString());
            return doctorEntity;
        };

        return this.jdbcTemplate.query(SELECT_ALL,rowMapper);
    }

    @Override
    public Iterable<DoctorEntity> findAllById(Iterable<Integer> integers) {
        return null;
    }

    /**
     * @operation: count all Doctors
     * @return: Number
     * */
    @Override
    public long count() {

        return this.jdbcTemplate.queryForObject(COUNT_DOCTOR,Long.class);
    }

    /**
     * @operation: delete by DoctorEntity id
     * @param: Integer
     * @return: -
     * */
    @Override
    public void deleteById(Integer integer) {
        this.jdbcTemplate.update(DELETE_BY_ID,integer);
    }

    /**
     * @operation: delete by DoctorEntity object
     * @param: DoctorEntity
     * @return: -
     * */
    @Override
    public void delete(DoctorEntity entity) {
        this.deleteById(entity.getId());
    }

    /**
     * @operation: delete all by id
     * @param: Interable --> Integer
     * @return: -
     * */
    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {
        Iterator iterator = integers.iterator();
        while (iterator.hasNext())
            this.deleteById(Integer.parseInt(iterator.next().toString()));
    }

    @Override
    public void deleteAll(Iterable<? extends DoctorEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private String getFilterQuery(String query, Map<String,String> filter){
        String SELECT_FILTER = query.concat(" WHERE ");
        Iterator<Map.Entry<String,String>> iterator = filter.entrySet().iterator();

        iterator.forEachRemaining(entry ->{
            String colum =  entry.getKey();
            String value = entry.getValue();

            SELECT_FILTER.concat(colum + "="+value);

            if(iterator.hasNext()){
                SELECT_FILTER.concat(" AND ");
            }
        });

        return SELECT_FILTER;
    }

    /**
     * @operation: Filter find
     * @param: Map --> key=column, value=data
     * @return: List --> UserDTO
     * */
    public List<DoctorEntity> filterFind(Map<String,String> filter){

        RowMapper<DoctorEntity> rowMapper = (rs,rowNum)->{

            DoctorEntity doctorEntity = DoctorEntity.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .lastname(rs.getString("lastname"))
                    .dni(rs.getInt("dni"))
                    .dateCreated(LocalDate.parse(rs.getString("dateCreated")))
                    .email(rs.getString("email"))
                    .build();

            if(rs.getString("dateDeleted") != null)
                doctorEntity.setDateDeleted(LocalDate.parse(rs.getString("dateDeleted")));
            else
                doctorEntity.setDateDeleted(null);

            doctorEntity.setUser(UserEntity.builder()
                    .username(rs.getString("username"))
                    .build());
            doctorEntity.setCredential(CredentialEntity.builder()
                    .id(rs.getInt("idCredential"))
                    .especiality(rs.getString("especiality"))
                    .licensenumber(rs.getString("licenseNumber"))
                    .build());

            LOGGER.trace("Info: DoctorRepository : findAll : "+ LocalDate.now().toString());
            return doctorEntity;
        };

        /**
         * PASAMOS EL FILTRO
         * */
        return this.jdbcTemplate.query(getFilterQuery(SELECT_ALL,filter),rowMapper);

    }
}
