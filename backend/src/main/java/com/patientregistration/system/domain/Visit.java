package com.patientregistration.system.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.View.Views;
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
    @Column(name = "id", nullable = false)
    @JsonView(Views.Basic.class)
    private Long id;

    @Column(name = "start", nullable = false)
    @JsonView(Views.Basic.class)
    private LocalDateTime start;

    @Column(name = "end_", nullable = false)
    @JsonView(Views.Basic.class)
    private LocalDateTime end;

    @Column(name = "status", nullable = false)
    @JsonView(Views.Basic.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "id_visit_model")
    @JsonView(Views.Basic.class)
    private VisitModel visitModel;

    @ManyToOne
    @JsonView(Views.Basic.class)
    @JoinColumn(name = "id_user")
    private User user;

    public Visit(LocalDateTime start, LocalDateTime end, String text, VisitModel visitModel) {
        this.start = start;
        this.end = end;
        this.text = text;
        this.visitModel = visitModel;
    }

}
