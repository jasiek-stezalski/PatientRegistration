package com.patientregistration.system.service;

import com.patientregistration.system.domain.VisitModel;

import java.util.List;

public interface VisitModelService {

    List<VisitModel> findAllVisitModels();

    VisitModel findByIdVisitModel(Long idVisitModel);

    VisitModel save(VisitModel visitModel);

    void delete(Long idVisitModel);

}
