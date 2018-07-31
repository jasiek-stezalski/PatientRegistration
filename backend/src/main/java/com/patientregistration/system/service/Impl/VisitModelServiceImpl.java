package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.UserRepository;
import com.patientregistration.system.repository.VisitModelRepository;
import com.patientregistration.system.service.VisitModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitModelServiceImpl implements VisitModelService {

    private VisitModelRepository visitModelRepository;
    private UserRepository userRepository;

    public VisitModelServiceImpl(VisitModelRepository visitModelRepository, UserRepository userRepository) {
        this.visitModelRepository = visitModelRepository;
        this.userRepository = userRepository;
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
    public VisitModel saveOrUpdate(VisitModel visitModel) {
        return visitModelRepository.save(visitModel);
    }

    @Override
    public void delete(Long idVisitModel) {
        visitModelRepository.deleteById(idVisitModel);
    }

    @Override
    public List<VisitModel> findAllByIdDoctor(Long idDoctor) {
        return visitModelRepository.findAllByUser(idDoctor);
    }

    @Override
    public List<VisitModel> findAllBySpecialization(String specialization) {
        return visitModelRepository.findAllBySpecialization(specialization);
    }

}
