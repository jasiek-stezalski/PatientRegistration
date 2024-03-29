package com.patientregistration.system.service;

import com.patientregistration.system.domain.Clinic;

import java.util.List;

public interface ClinicService {

    List<Clinic> findAllClinics();

    List<Clinic> findAllClinicsByUser(Long idUser);

}
