package com.SpringBoot.Clinica.Repository;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.Entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ActiveProfiles("test")
class CredentialRepositoryTest {

    @Autowired
    private CredentialRepository credentialRepository;

    @Test
    void save() {
        //GIVEN
        CredentialEntity credential = CredentialEntity.builder()
                .especiality("Cirujano")
                .licensenumber("11585144788")
                .institute("Harvard")
                .graduateDate(LocalDate.now())
                .name_lastname("Mirta Avila")
                .build();

        CredentialEntity c = credentialRepository.save(credential);

        //TEST
        assertThat(c).isNotNull();
        assertThat(c.getName_lastname()).isEqualTo(credential.getName_lastname());
        assertThat(c instanceof CredentialEntity).isTrue();
        assertNull(c.getId(),"El id deberia ser nulo");
        assertNotNull(c.getName_lastname(),"El nombre de la credencial deberia ser nulo");
    }

    @Test
    void findById() {
        //GIVEN
        Optional<CredentialEntity> c = this.credentialRepository.findById(1);

        //TEST
        assertNotNull(c);
        assertThat(c.get() instanceof CredentialEntity).isTrue();
        assertNotNull(c.get().getId());
    }

    @Test
    void existsById() {

        Boolean isExists = this.credentialRepository.existsById(1);
        Boolean isNotExists = this.credentialRepository.existsById(10000);

        assertTrue(isExists);
        assertFalse(isNotExists);

    }

    @Test
    void findAll() {
        Iterable<CredentialEntity> credentialEntityIterable = this.credentialRepository.findAll();
        List<CredentialEntity> credentialEntityList = new ArrayList<>();

        credentialEntityIterable.forEach(e ->{
            credentialEntityList.add(e);
        });

        assertThat(credentialEntityIterable instanceof Iterable<CredentialEntity>).isTrue();
        assertThat(credentialEntityList.get(0) != null).isTrue();
        assertThat(credentialEntityList.isEmpty()).isFalse();
    }

    @Test
    void findAllById() {
        Long cantCredential = this.credentialRepository.count();
        assertThat(cantCredential).isNotNull();
    }

    @Test
    void deleteById() {
        CredentialEntity credential = CredentialEntity.builder()
                .especiality("Cirujano")
                .licensenumber("11585144788")
                .institute("Harvard")
                .graduateDate(LocalDate.now())
                .name_lastname("Mirta Avila")
                .build();

        this.credentialRepository.save(credential);
        CredentialEntity credentialDB = this.credentialRepository.find_by_credential_number(credential.getLicensenumber()).get();
        this.credentialRepository.deleteById(credentialDB.getId());
        System.out.println(this.credentialRepository.find_by_credential_number(credential.getLicensenumber()));
        assertNull(this.credentialRepository.find_by_credential_number(credential.getLicensenumber()));
    }

}