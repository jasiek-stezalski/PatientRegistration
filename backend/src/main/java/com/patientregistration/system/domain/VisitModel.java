package com.patientregistration.system.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.View.Views;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "name", nullable = false)
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

    @Column(name = "care_type", nullable = false)
    @JsonView(Views.Basic.class)
    private String careType;

    @Column(name = "price", nullable = false)
    @JsonView(Views.Basic.class)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    @JsonView(Views.Basic.class)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_clinic")
    @JsonView(Views.Basic.class)
    private Clinic clinic;

    @OneToMany(mappedBy = "visitModel", cascade = CascadeType.ALL)
    private List<Visit> visits;

}
