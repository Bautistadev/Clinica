package com.SpringBoot.Clinica.Service.Mapper;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.model.CredentialDTO;
import com.SpringBoot.Clinica.model.CredentialRequestDTO;

public interface CredentialMapper {
    public CredentialEntity map(CredentialRequestDTO credentialRequestDTO);
    public CredentialEntity map(CredentialDTO credentialDTO);
    public CredentialDTO map(CredentialEntity credentialEntity);
}
