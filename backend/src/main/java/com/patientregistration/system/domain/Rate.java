package com.patientregistration.system.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "rate")
@Data
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", nullable = false)
    private Integer value;

}
