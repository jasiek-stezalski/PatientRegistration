package com.patientregistration.system.service;

import com.patientregistration.system.domain.VisitHour;

import java.util.List;

public interface VisitHourService {

    List<VisitHour> findAllVisitHours();

    VisitHour findByVisitHourId(Long idVisitHour);

    VisitHour saveOrUpdate(VisitHour visitHour);

    void delete(Long idVisitHour);

}
