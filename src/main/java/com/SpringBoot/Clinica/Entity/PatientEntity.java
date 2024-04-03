package com.SpringBoot.Clinica.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PatientEntity{

    private Integer id;
    private String name;
    private String lastname;
    private Integer dni;
    private LocalDate birthdate;
    private String gender;
    private String email;
    private String address;
    private LocalDate dateCreated;
    private LocalDate dateDeleted;

    private List<TelephoneEntity> telephoneEntity;

    @Override
    public String toString() {
        return "PatientEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dni=" + dni +
                ", birthdate=" + birthdate +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateDeleted=" + dateDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientEntity that = (PatientEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(lastname, that.lastname) && Objects.equals(dni, that.dni) && Objects.equals(birthdate, that.birthdate) && Objects.equals(gender, that.gender) && Objects.equals(email, that.email) && Objects.equals(address, that.address) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateDeleted, that.dateDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, dni, birthdate, gender, email, address, dateCreated, dateDeleted);
    }
}
