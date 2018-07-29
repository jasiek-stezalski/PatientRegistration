package com.patientregistration.system.service;

import com.patientregistration.system.domain.Visit;

import java.util.List;

public interface VisitService {

    List<Visit> findAllVisits();

    Visit findByVisitId(Long idVisit);

    Visit saveOrUpdate(Visit visit);

    void delete(Long idVisit);

}
