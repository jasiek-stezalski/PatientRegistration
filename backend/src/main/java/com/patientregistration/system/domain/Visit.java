package com.patientregistration.system.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "visit")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visit", nullable = false)
    private Long idVisit;

    @Column(name = "visit_date", nullable = false)
    private Date visitDate;

    @ManyToOne
    @JoinColumn(name = "id_visit_model")
    private VisitModel visitModel;

//    @OneToMany
//    @JoinColumn(name = "id_visit")
//    private List<VisitHour> visitHours;

    public Visit(Date visitDate, VisitModel visitModel) {
        this.visitDate = visitDate;
        this.visitModel = visitModel;
    }

}
