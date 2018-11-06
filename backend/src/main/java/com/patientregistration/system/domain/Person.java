package com.patientregistration.system.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.View.Views;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(Views.Basic.class)
    private Long id;

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    @JsonView(Views.Basic.class)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    @JsonView(Views.Basic.class)
    private String lastName;

    @Column(name = "pesel", nullable = false, unique = true)
    @JsonView(Views.Basic.class)
    private String pesel;

    @Column(name = "insurance", nullable = false)
    @JsonView(Views.Basic.class)
    private Boolean insurance;

    @Column(name = "specialization")
    @JsonView(Views.Basic.class)
    private String specialization;

}
