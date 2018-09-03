package com.patientregistration.system.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "statement")
@Data
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_statement", nullable = false)
    private long idStatement;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "read", nullable = false)
    private Boolean read;

}
