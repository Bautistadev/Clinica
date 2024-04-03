package com.SpringBoot.Clinica.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorEntity {

    private Integer id;
    private String name;
    private String lastname;
    private Integer dni;
    private String email;
    private LocalDate dateCreated;
    private LocalDate dateDeleted;

    private UserEntity user;
    private CredentialEntity credential;

    private List<TelephoneEntity> telephoneEntity;

    @Override
    public String toString() {
        return "DoctorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dni=" + dni +
                ", email='" + email + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateDeleted=" + dateDeleted +
                ", user=" + user +
                ", credential=" + credential +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorEntity that = (DoctorEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(lastname, that.lastname) && Objects.equals(dni, that.dni) && Objects.equals(email, that.email) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateDeleted, that.dateDeleted) && Objects.equals(user, that.user) && Objects.equals(credential, that.credential);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, dni, email, dateCreated, dateDeleted, user, credential);
    }
}
