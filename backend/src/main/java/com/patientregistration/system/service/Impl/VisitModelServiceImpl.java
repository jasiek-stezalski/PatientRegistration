package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitModelRepository;
import com.patientregistration.system.service.VisitModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitModelServiceImpl implements VisitModelService {

    private VisitModelRepository visitModelRepository;

    public VisitModelServiceImpl(VisitModelRepository visitModelRepository) {
        this.visitModelRepository = visitModelRepository;
    }

    @Override
    public List<VisitModel> findAllVisitModels() {
        return visitModelRepository.findAll();
    }

    @Override
    public VisitModel findByVisitId(Long idVisitModel) {
        return visitModelRepository.findById(idVisitModel)
                .orElseThrow(() -> new ResourceNotFoundException(idVisitModel.toString()));
    }

    @Override
    public VisitModel saveOrUpdate(VisitModel visitModel) {
        return visitModelRepository.save(visitModel);
    }

    @Override
    public void delete(Long idVisitModel) {
        visitModelRepository.deleteById(idVisitModel);
    }

}
