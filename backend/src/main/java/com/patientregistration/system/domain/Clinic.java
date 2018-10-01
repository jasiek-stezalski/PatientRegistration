package com.patientregistration.system.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.View.Views;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clinic")
@Data
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(Views.Basic.class)
    private Long id;

    @Column(name = "name", nullable = false)
    @JsonView(Views.Basic.class)
    private String name;

    @Column(name = "medical_center", nullable = false)
    @JsonView(Views.Basic.class)
    private String medicalCenter;

    @Column(name = "address", nullable = false)
    @JsonView(Views.Basic.class)
    private String address;

    @Column(name = "city", nullable = false)
    @JsonView(Views.Basic.class)
    private String city;

    @Column(name = "phone_number")
    @JsonView(Views.Basic.class)
    private String phoneNumber;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<VisitModel> visitModels;

}
