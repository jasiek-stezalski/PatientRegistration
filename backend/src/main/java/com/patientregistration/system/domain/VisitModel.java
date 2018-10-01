package com.patientregistration.system.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.View.Views;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "visit_model")
@Data
public class VisitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(Views.Basic.class)
    private Long id;

    @Column(name = "text", nullable = false)
    @JsonView(Views.Basic.class)
    private String text;

    @Column(name = "start", nullable = false)
    @JsonView(Views.Basic.class)
    private LocalDateTime start;

    @Column(name = "end_", nullable = false)
    @JsonView(Views.Basic.class)
    private LocalDateTime end;

    @Column(name = "end_date", nullable = false)
    @JsonView(Views.Basic.class)
    private Date endDate;

    @Column(name = "day_interval", nullable = false)
    @JsonView(Views.Basic.class)
    private Integer dayInterval;

    @Column(name = "minute_interval", nullable = false)
    @JsonView(Views.Basic.class)
    private Integer minuteInterval;

    @Column(name = "specialization", nullable = false)
    @JsonView(Views.Basic.class)
    private String specialization;

    @Column(name = "care_type", nullable = false)
    @JsonView(Views.Basic.class)
    private String careType;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_clinic")
    private Clinic clinic;

    @OneToMany(mappedBy = "visitModel", cascade = CascadeType.ALL)
    private List<Visit> visits;

}
