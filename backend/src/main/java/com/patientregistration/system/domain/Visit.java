package com.patientregistration.system.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "visit")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visit", nullable = false)
    private Long idVisit;

    @Column(name = "visit_date", nullable = false)
    private Date visitDate;

    @OneToMany
    @JoinColumn(name = "id_visit")
    private List<VisitHour> visitHours;

}
