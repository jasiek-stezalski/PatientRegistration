package com.patientregistration.system.service;

import com.patientregistration.system.domain.Clinic;

import java.util.List;

public interface ClinicService {

    List<Clinic> findAllClinics();

    Clinic findByClinicId(Long idClinic);

    Clinic saveOrUpdate(Clinic clinic);

    void delete(Long idClinic);

}
