package com.patientregistration.system.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "email")
    private String email;

    @Column(name = "blocked")
    private Boolean blocked;

    @Column(name = "phone_number")
    private String phoneNumber;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<VisitModel> visitModels;

//    @OneToMany
//    @JoinColumn(name = "id_user")
//    private List<VisitHour> visitHours;

    @OneToMany
    @JoinColumn(name = "id_user")
    private List<Statement> statements;

}
