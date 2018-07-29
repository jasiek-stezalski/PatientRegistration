package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.Clinic;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.ClinicRepository;
import com.patientregistration.system.service.ClinicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicServiceImpl implements ClinicService {

    private ClinicRepository clinicRepository;

    public ClinicServiceImpl(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public List<Clinic> findAllClinics() {
        return clinicRepository.findAll();
    }

    @Override
    public Clinic findByClinicId(Long idClinic) {
        return clinicRepository.findById(idClinic)
                .orElseThrow(() -> new ResourceNotFoundException(idClinic.toString()));
    }

    @Override
    public Clinic saveOrUpdate(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    @Override
    public void delete(Long idClinic) {
        clinicRepository.deleteById(idClinic);
    }

}
