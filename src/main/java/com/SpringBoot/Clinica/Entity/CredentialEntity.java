package com.SpringBoot.Clinica.Entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class CredentialEntity {

    private Integer id;
    private String name_lastname;
    private String licensenumber;
    private String especiality;
    private String institute;
    private LocalDate graduateDate;

    @Override
    public String toString() {
        return "CredentialEntity{" +
                "id=" + id +
                ", name_lastname='" + name_lastname + '\'' +
                ", licensenumber='" + licensenumber + '\'' +
                ", especiality='" + especiality + '\'' +
                ", institute='" + institute + '\'' +
                ", graduateDate=" + graduateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialEntity that = (CredentialEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name_lastname, that.name_lastname) && Objects.equals(licensenumber, that.licensenumber) && Objects.equals(especiality, that.especiality) && Objects.equals(institute, that.institute) && Objects.equals(graduateDate, that.graduateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name_lastname, licensenumber, especiality, institute, graduateDate);
    }
}