package com.SpringBoot.Clinica.Service.Mapper;

import com.SpringBoot.Clinica.Entity.DoctorEntity;
import com.SpringBoot.Clinica.model.DoctorDTO;
import com.SpringBoot.Clinica.model.DoctorRequestDTO;

public interface DoctorMapper {
    public DoctorDTO map(DoctorEntity doctorEntity) throws Exception;
    public DoctorEntity map(DoctorRequestDTO doctorRequestDTO);
    public DoctorEntity map(DoctorDTO doctorDTO);
}
