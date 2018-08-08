package com.patientregistration.system.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "visit_hour")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class VisitHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visit_hour", nullable = false)
    private Long idVisitHour;

    @Column(name = "hour", nullable = false)
    private Time hour;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_visit")
    private Visit visit;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public VisitHour(Time hour, Visit visit) {
        this.hour = hour;
        this.visit = visit;
    }

}
