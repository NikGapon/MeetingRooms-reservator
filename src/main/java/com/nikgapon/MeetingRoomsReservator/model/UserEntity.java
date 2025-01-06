package com.nikgapon.MeetingRoomsReservator.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "users")
@ToString
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    @NotBlank
    @Size(min = 3, max = 64)
    @Getter
    @Setter
    private String login;

    @Column(nullable = false, length = 64)
    @NotBlank
    @Size(min = 6, max = 64)
    @Getter
    @Setter
    private String password;

    @Getter
    private UserRole role;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 128)
    @Getter
    @Setter
    private String FIO;

    public UserEntity(String login, String password) {
        this(login, password, UserRole.USER);
    }

    public UserEntity(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = UserRole.valueOf(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

}