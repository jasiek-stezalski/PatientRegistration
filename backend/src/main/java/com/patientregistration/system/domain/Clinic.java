package com.patientregistration.system.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clinic")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clinic", nullable = false)
    private Long idClinic;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "medical_center", nullable = false)
    private String medicalCenter;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany
    @JoinColumn(name = "id_clinic")
    private List<VisitModel> visitModels;

}
