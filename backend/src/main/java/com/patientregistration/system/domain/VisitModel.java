package com.patientregistration.system.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "visit_model")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VisitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visit_model", nullable = false)
    private Long idVisitModel;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "day_interval", nullable = false)
    private Integer dayInterval;

    @Column(name = "begin_time", nullable = false)
    private Time beginTime;

    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @Column(name = "minute_interval", nullable = false)
    private Integer minuteInterval;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private User user;

    @OneToMany()
    @JoinColumn(name = "id_visit_model")
    private List<Visit> visits;

}
