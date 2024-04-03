package com.SpringBoot.Clinica.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelephoneEntity {

    private Integer id;
    private Integer areaCod;
    private Integer number;


    @Override
    public String toString() {
        return "TelephoneEntity{" +
                "id=" + id +
                ", areaCod=" + areaCod +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelephoneEntity that = (TelephoneEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(areaCod, that.areaCod) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, areaCod, number);
    }
}
