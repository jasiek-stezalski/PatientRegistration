package com.patientregistration.system.service.Impl;

import com.patientregistration.system.repository.VisitHourRepository;
import com.patientregistration.system.service.VisitHourService;
import org.springframework.stereotype.Service;

@Service
public class VisitHourServiceimpl implements VisitHourService {

    private VisitHourRepository visitHourRepository;

    public VisitHourServiceimpl(VisitHourRepository visitHourRepository) {
        this.visitHourRepository = visitHourRepository;
    }

}
