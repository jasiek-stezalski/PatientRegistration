package com.patientregistration.system.service.Impl;

import com.patientregistration.system.repository.VisitModelRepository;
import com.patientregistration.system.service.VisitModelService;
import org.springframework.stereotype.Service;

@Service
public class VisitModelServiceImpl implements VisitModelService {

    private VisitModelRepository visitModelRepository;

    public VisitModelServiceImpl(VisitModelRepository visitModelRepository) {
        this.visitModelRepository = visitModelRepository;
    }

}
