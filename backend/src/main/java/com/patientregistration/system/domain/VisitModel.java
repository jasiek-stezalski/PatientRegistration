package com.patientregistration.system.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;


@Entity
@Table(name = "visit_model")
@Data
public class VisitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visit_model", nullable = false)
    private Long idVisitModel;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end_", nullable = false)
    private LocalDateTime end;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "day_interval", nullable = false)
    private Integer dayInterval;

    @Column(name = "minute_interval", nullable = false)
    private Integer minuteInterval;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "care_type", nullable = false)
    private String careType;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_clinic")
    private Clinic clinic;

//    @OneToMany()
//    @JoinColumn(name = "id_visit_model")
//    private List<Visit> visits;

}
