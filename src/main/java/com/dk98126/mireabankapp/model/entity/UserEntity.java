package com.dk98126.mireabankapp.model.entity;

import com.dk98126.mireabankapp.model.enm.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    String login;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "boolean default false")
    private boolean blocked;

    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "^7\\d{10}$")
    private String phoneNumber;

}
