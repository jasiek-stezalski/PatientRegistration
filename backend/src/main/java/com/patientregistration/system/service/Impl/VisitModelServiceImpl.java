package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitModelRepository;
import com.patientregistration.system.service.VisitModelService;
import com.patientregistration.system.service.VisitService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class VisitModelServiceImpl implements VisitModelService {

    private VisitModelRepository visitModelRepository;
    private VisitService visitService;

    public VisitModelServiceImpl(VisitModelRepository visitModelRepository, VisitService visitService) {
        this.visitModelRepository = visitModelRepository;
        this.visitService = visitService;
    }

    @Override
    public List<VisitModel> findAllVisitModels() {
        return visitModelRepository.findAll();
    }

    @Override
    public VisitModel findByIdVisitModel(Long idVisitModel) {
        return visitModelRepository.findById(idVisitModel)
                .orElseThrow(() -> new ResourceNotFoundException(idVisitModel.toString()));
    }

    @Override
    public VisitModel save(VisitModel visitModel) {
        VisitModel newVisitModel = visitModelRepository.save(visitModel);

        LocalDate visitDate = visitModel.getStartDate().toLocalDate();
        LocalDate endDate = visitModel.getEndDate().toLocalDate();

        while (visitDate.isBefore(endDate) || visitDate.isEqual(endDate)) {
            visitService.save(new Visit(Date.valueOf(visitDate), newVisitModel));
            visitDate = visitDate.plusDays(visitModel.getDayInterval());
        }

        return newVisitModel;
    }

    @Override
    public void delete(Long idVisitModel) {
        visitModelRepository.deleteById(idVisitModel);
    }

}
