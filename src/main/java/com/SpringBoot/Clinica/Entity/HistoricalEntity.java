package com.SpringBoot.Clinica.Entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricalEntity {

    private Integer id;
    private String diagnosis;
    private String tratment;
    private String recomended;
    private String result;
    private AppointmentEntity appointmentEntity;
    private PatientEntity patientEntity;
    private PrescriptionEntity prescriptionEntity;

    @Override
    public String toString() {
        return "HistoricalEntity{" +
                "id=" + id +
                ", diagnosis='" + diagnosis + '\'' +
                ", tratment='" + tratment + '\'' +
                ", recomended='" + recomended + '\'' +
                ", result='" + result + '\'' +
                ", appointmentEntity=" + appointmentEntity +
                ", patientEntity=" + patientEntity +
                ", prescription=" + prescriptionEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalEntity that = (HistoricalEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(diagnosis, that.diagnosis) && Objects.equals(tratment, that.tratment) && Objects.equals(recomended, that.recomended) && Objects.equals(result, that.result) && Objects.equals(appointmentEntity, that.appointmentEntity) && Objects.equals(patientEntity, that.patientEntity) && Objects.equals(prescriptionEntity, that.prescriptionEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, diagnosis, tratment, recomended, result, appointmentEntity, patientEntity, prescriptionEntity);
    }
}
