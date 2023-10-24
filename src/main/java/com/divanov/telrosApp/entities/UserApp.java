package com.divanov.telrosApp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private String username;
    @Column(name = "password")
    @NotEmpty
    private String password;
    @Column(name = "first_name")
    @NotEmpty
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty
    private String lastName;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "birth_date")
    @NotEmpty
    private String dateOfBirth;
    @Column(name = "email")
    @NotEmpty
    private String email;
    @Column(name = "phone")
    private String phone;

    @ManyToMany
    @NotNull
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
}
