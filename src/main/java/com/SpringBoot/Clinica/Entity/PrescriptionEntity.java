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
public class PrescriptionEntity {
    private Integer id;
    private LocalDate prescription_date;
    private String dose;
    private String instruction;
    private MedicineEntity medicineEntity;

    @Override
    public String toString() {
        return "PrescriptionEntity{" +
                "id=" + id +
                ", prescription_date=" + prescription_date +
                ", dose='" + dose + '\'' +
                ", instruction='" + instruction + '\'' +
                ", medicineEntity=" + medicineEntity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionEntity that = (PrescriptionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(prescription_date, that.prescription_date) && Objects.equals(dose, that.dose) && Objects.equals(instruction, that.instruction) && Objects.equals(medicineEntity, that.medicineEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prescription_date, dose, instruction, medicineEntity);
    }
}
