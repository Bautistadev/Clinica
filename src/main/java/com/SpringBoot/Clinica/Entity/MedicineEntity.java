package com.SpringBoot.Clinica.Entity;

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
public class MedicineEntity {

    private Integer id;
    private String name;
    private String description_medicine;
    private String instruction;
    private LocalDate dateCreated;
    private LocalDate dateDeleted;

    @Override
    public String toString() {
        return "MedicineEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description_medicine='" + description_medicine + '\'' +
                ", instruction='" + instruction + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateDeleted=" + dateDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineEntity that = (MedicineEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description_medicine, that.description_medicine) && Objects.equals(instruction, that.instruction) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateDeleted, that.dateDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description_medicine, instruction, dateCreated, dateDeleted);
    }
}
