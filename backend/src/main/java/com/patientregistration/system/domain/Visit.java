package com.patientregistration.system.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
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

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
    private List<VisitHour> visitHours;

    @ManyToOne
    @JoinColumn(name = "id_visit_model")
    private VisitModel visitModel;

}
