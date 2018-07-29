package com.patientregistration.system.service.Impl;

import com.patientregistration.system.repository.VisitRepository;
import com.patientregistration.system.service.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

}
