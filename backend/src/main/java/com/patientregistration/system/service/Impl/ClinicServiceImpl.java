package com.patientregistration.system.service.Impl;

import com.patientregistration.system.repository.ClinicRepository;
import com.patientregistration.system.service.ClinicService;
import org.springframework.stereotype.Service;

@Service
public class ClinicServiceImpl implements ClinicService {

    private ClinicRepository clinicRepository;

    public ClinicServiceImpl(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

}
