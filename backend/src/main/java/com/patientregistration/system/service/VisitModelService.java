package com.patientregistration.system.service;

import com.patientregistration.system.domain.VisitModel;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitModelService {

    List<VisitModel> findBetweenByDoctor(LocalDateTime from, LocalDateTime to, Long idUser);

    VisitModel findByIdVisitModel(Long idVisitModel);

    VisitModel save(VisitModel visitModel);

    void delete(Long idVisitModel);

    VisitModel move(VisitModel data);

}
