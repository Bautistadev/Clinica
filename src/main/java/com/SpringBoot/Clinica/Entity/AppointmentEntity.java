package com.SpringBoot.Clinica.Entity;

import com.SpringBoot.Clinica.Entity.Enum.AppointmentStatus;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentEntity {

    private Integer id;
    private AppointmentStatus appointmentStatus;
    private LocalDate appointmentDate;
    private LocalDate dateCreated;
    private LocalDate dateDeleted;

    private PatientEntity patientEntity;
    private DoctorEntity doctorEntity;

    @Override
    public String toString() {
        return "AppointmentEntity{" +
                "id=" + id +
                ", appointmentStatus=" + appointmentStatus +
                ", appointmentDate=" + appointmentDate +
                ", dateCreated=" + dateCreated +
                ", dateDeleted=" + dateDeleted +
                ", patientEntity=" + patientEntity +
                ", doctorEntity=" + doctorEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentEntity that = (AppointmentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(appointmentStatus, that.appointmentStatus) && Objects.equals(appointmentDate, that.appointmentDate) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateDeleted, that.dateDeleted) && Objects.equals(patientEntity, that.patientEntity) && Objects.equals(doctorEntity, that.doctorEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appointmentStatus, appointmentDate, dateCreated, dateDeleted, patientEntity, doctorEntity);
    }
}
