package com.patientregistration.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.View.Views;
import lombok.Data;

import javax.persistence.*;

@Entity
@IdClass(CompositeKey.class)
@Table(name = "doctor_clinics")
@Data
public class DoctorClinic {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_doctor")
    @JsonIgnore
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_clinic")
    @JsonView(Views.Basic.class)
    private Clinic clinic;
}
