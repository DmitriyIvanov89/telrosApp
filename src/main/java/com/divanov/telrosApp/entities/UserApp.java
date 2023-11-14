package com.divanov.telrosApp.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name = "users")
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotEmpty
    @Pattern(regexp = "^[\\w+\\-]{3,50}", message = "Only Latin letters, '_' and numbers. Min 3 and Max 50 symbols")
    private String username;

    @Column(name = "password")
    @NotEmpty
    @Pattern(regexp = ".{10,50}", message = "Min 10 and Max 50 symbols")
    private String password;

    @Column(name = "first_name")
    @NotEmpty
    @Pattern(regexp = "^([A-ZА-Я]{1}[a-zа-я\\-]+)([A-ZА-Яa-zа-я]+){3,50}", message = "Only letters and '-'. The first letter must be capitalized. Min 3 and Max 50 symbols")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @Pattern(regexp = "^([A-ZА-Я]{1}[a-zа-я\\-]+)([A-ZА-Яa-zа-я]+){3,50}", message = "Only letters and '-'. The first letter must be capitalized. Min 3 and Max 50 symbols")
    private String lastName;

    @Column(name = "patronymic")
    @Pattern(regexp = "^([A-ZА-Я]{1}[a-zа-я\\-]+)([A-ZА-Яa-zа-я]+){3,50}", message = "Only letters and '-'. The first letter must be capitalized. Min 3 and Max 50 symbols")
    private String patronymic;

    @Column(name = "birth_date")
    @NotEmpty
    @Pattern(regexp = "\\d{4}\\-\\d{2}\\-\\d{2}", message = "Invalid date format")
    private String dateOfBirth;

    @Column(name = "email")
    @NotEmpty
    @Pattern(regexp = "\\w+@\\w+\\.\\w{2,3}", message = "Invalid email format")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp = "^\\+{1}7\\(\\d{3}\\)\\d{3}\\-\\d{2}\\-\\d{2}$", message = "Invalid phone format")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull
    private Role role;
}
