package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitRepository;
import com.patientregistration.system.service.VisitService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Visit> findAllVisits() {
        return visitRepository.findAll();
    }

    @Override
    public Visit findByVisitId(Long idVisit) {
        return visitRepository.findById(idVisit)
                .orElseThrow(() -> new ResourceNotFoundException(idVisit.toString()));
    }

    @Override
    public Visit saveOrUpdate(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public void delete(Long idVisit) {
        visitRepository.deleteById(idVisit);
    }

}
