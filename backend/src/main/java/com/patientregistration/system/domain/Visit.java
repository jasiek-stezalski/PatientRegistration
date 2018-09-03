package com.patientregistration.system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visit")
@Data
@NoArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visit", nullable = false)
    private Long idVisit;

    @Column(name = "term", nullable = false)
    private LocalDateTime term;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_visit_model")
    private VisitModel visitModel;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Visit(LocalDateTime term, VisitModel visitModel) {
        this.term = term;
        this.visitModel = visitModel;
    }
}
