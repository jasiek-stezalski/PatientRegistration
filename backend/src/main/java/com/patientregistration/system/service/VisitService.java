package com.patientregistration.system.service;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.domain.VisitModel;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitService {

    List<Visit> findBetween(LocalDateTime from, LocalDateTime to);

    List<Visit> findAllVisitsByIdUser(Long idUser);

    Visit findByVisitId(Long idVisit);

    Visit save(Visit visit);

    Visit bookVisit(Long idVisit, Long idUser);

    void delete(Long idVisit);

    List<Visit> findAllByVisitModel(VisitModel savedVisitModel);

}
