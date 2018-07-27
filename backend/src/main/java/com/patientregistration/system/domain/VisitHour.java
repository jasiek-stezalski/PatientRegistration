package com.patientregistration.system.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Time;

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

    @ManyToOne
    @JoinColumn(name = "id_visit")
    private Visit visit;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}
