package com.SpringBoot.Clinica.Entity;


import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class UserEntity {

    private Integer id;
    private String username;
    private String password;
    private Role role;
    private Status status;
    private LocalDate dateCreated;
    private LocalDate dateDeleted;

    public UserEntity(Integer id, String username, String password, Role role, Status status, LocalDate dateCreated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", dateCreated=" + dateCreated +
                ", dateDeleted=" + dateDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(role, that.role) && Objects.equals(status, that.status) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateDeleted, that.dateDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, status, dateCreated, dateDeleted);
    }
}
