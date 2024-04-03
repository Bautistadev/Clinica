package com.SpringBoot.Clinica.Service.Mapper;

import com.SpringBoot.Clinica.Entity.DoctorEntity;
import com.SpringBoot.Clinica.model.DoctorDTO;
import com.SpringBoot.Clinica.model.DoctorRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class DoctorMapperImplements implements DoctorMapper{

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorMapperImplements.class);

    private CredentialMapper credentialMapper;
    private UserMapper userMapper;

    public DoctorMapperImplements(CredentialMapper credentialMapper,UserMapper userMapper) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
    }

    @Override
    public DoctorDTO map(DoctorEntity doctorEntity) throws Exception {
        try {
            DoctorDTO doctorDTO = new DoctorDTO()
                    .id(doctorEntity.getId())
                    .dni(doctorEntity.getDni())
                    .email(doctorEntity.getEmail())
                    .dateCreated(doctorEntity.getDateCreated())
                    .name(doctorEntity.getName())
                    .credentials(this.credentialMapper.map(doctorEntity.getCredential()))
                    .lastname(doctorEntity.getLastname())
                    .userCredential(this.userMapper.map(doctorEntity.getUser()));

            if(doctorEntity.getDateDeleted() !=null)
                doctorDTO.dateDeleted(doctorEntity.getDateDeleted());
            else
                doctorDTO.dateDeleted(null);

            LOGGER.trace("Info : DoctorMapperImplements : map : DoctorEntity --> DoctorDTO : " + LocalDateTime.now());
            return doctorDTO;
        }catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.error("Error : DoctorMapperImplements : map : DoctorEntity --> DoctorDTO : "+ LocalDateTime.now());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public DoctorEntity map(DoctorRequestDTO doctorRequestDTO) {

        try {
            DoctorEntity doctorEntity = DoctorEntity.builder()
                    .dni(doctorRequestDTO.getDni())
                    .email(doctorRequestDTO.getEmail())
                    .dateCreated(doctorRequestDTO.getDateCreated())
                    .name(doctorRequestDTO.getName())
                    .credential(this.credentialMapper.map(doctorRequestDTO.getCredentials()))
                    .lastname(doctorRequestDTO.getLastname())
                    .user(this.userMapper.map(doctorRequestDTO.getUserCredential()))
                    .dateDeleted(null)
                    .build();



            LOGGER.trace("Info : DoctorMapperImplements : map : DoctorRequestDTO --> DoctorEntity : " + LocalDateTime.now());
            return doctorEntity;
        }catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.error("Error : DoctorMapperImplements : map : DoctorRequestDTO --> DoctorEntity : "+ LocalDateTime.now());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public DoctorEntity map(DoctorDTO doctorDTO) {
        try {
            DoctorEntity doctorEntity = DoctorEntity.builder()
                    .dni(doctorDTO.getDni())
                    .email(doctorDTO.getEmail())
                    .dateCreated(doctorDTO.getDateCreated())
                    .name(doctorDTO.getName())
                    .credential(this.credentialMapper.map(doctorDTO.getCredentials()))
                    .lastname(doctorDTO.getLastname())
                    .user(this.userMapper.map(doctorDTO.getUserCredential()))
                    .build();

            if(doctorDTO.getDateDeleted() !=null)
                doctorEntity.setDateDeleted(doctorDTO.getDateDeleted());
            else
                doctorEntity.setDateDeleted(null);

            LOGGER.trace("Info : DoctorMapperImplements : map : DoctorDTO --> DoctorEntity : " + LocalDateTime.now());
            return doctorEntity;
        }catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.error("Error : DoctorMapperImplements : map : DoctorDTO --> DoctorEntity : "+ LocalDateTime.now());
            throw new RuntimeException(e.getMessage());
        }
    }
}
