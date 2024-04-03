package com.SpringBoot.Clinica.Service.Mapper;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.model.CredentialDTO;
import com.SpringBoot.Clinica.model.CredentialRequestDTO;

public class CredentialMapperImplements implements CredentialMapper{
    @Override
    public CredentialEntity map(CredentialRequestDTO credentialRequestDTO) {
        CredentialEntity credential = CredentialEntity.builder()
                .name_lastname(credentialRequestDTO.getNameLastname())
                .licensenumber(credentialRequestDTO.getLicenseNumber())
                .especiality(credentialRequestDTO.getEspeciality())
                .institute(credentialRequestDTO.getIntitute())
                .graduateDate(credentialRequestDTO.getGraduationDate())
                .build();

        return credential;
    }

    @Override
    public CredentialEntity map(CredentialDTO credentialDTO) {
        CredentialEntity credential = CredentialEntity.builder()
                .id(credentialDTO.getId())
                .name_lastname(credentialDTO.getNameLastname())
                .graduateDate(credentialDTO.getGraduationDate())
                .especiality(credentialDTO.getEspeciality())
                .licensenumber(credentialDTO.getLicenseNumber())
                .institute(credentialDTO.getIntitute())
                .build();
        return credential;
    }

    @Override
    public CredentialDTO map(CredentialEntity credentialEntity) {
        CredentialDTO credentialDTO = new CredentialDTO()
                .id(credentialEntity.getId())
                .especiality(credentialEntity.getEspeciality())
                .graduationDate(credentialEntity.getGraduateDate())
                .intitute(credentialEntity.getInstitute())
                .licenseNumber(credentialEntity.getLicensenumber())
                .nameLastname(credentialEntity.getName_lastname());
        return credentialDTO;
    }
}
