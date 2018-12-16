package com.patientregistration.system.service.Impl;

import com.patientregistration.system.repository.RateRepository;
import com.patientregistration.system.service.RateService;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl implements RateService {

    private RateRepository rateRepository;

    public RateServiceImpl(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }
}
