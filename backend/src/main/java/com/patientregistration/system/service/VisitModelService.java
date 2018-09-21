package com.patientregistration.system.service;

import com.patientregistration.system.domain.VisitModel;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitModelService {

    List<VisitModel> findBetween(LocalDateTime from, LocalDateTime to);

    VisitModel findByIdVisitModel(Long idVisitModel);

    VisitModel save(VisitModel visitModel);

    void delete(Long idVisitModel);

    VisitModel move(VisitModel data);
}
