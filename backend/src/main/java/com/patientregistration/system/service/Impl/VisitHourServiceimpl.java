package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.VisitHour;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitHourRepository;
import com.patientregistration.system.service.VisitHourService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitHourServiceimpl implements VisitHourService {

    private VisitHourRepository visitHourRepository;

    public VisitHourServiceimpl(VisitHourRepository visitHourRepository) {
        this.visitHourRepository = visitHourRepository;
    }

    @Override
    public List<VisitHour> findAllVisitHours() {
        return visitHourRepository.findAll();
    }

    @Override
    public VisitHour findByVisitHourId(Long idVisitHour) {
        return visitHourRepository.findById(idVisitHour)
                .orElseThrow(() -> new ResourceNotFoundException(idVisitHour.toString()));
    }

    @Override
    public VisitHour saveOrUpdate(VisitHour visitHour) {
        return visitHourRepository.save(visitHour);
    }

    @Override
    public void delete(Long idVisitHour) {
        visitHourRepository.deleteById(idVisitHour);
    }

}
