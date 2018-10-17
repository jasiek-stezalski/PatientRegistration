package com.patientregistration.system.service;

import com.patientregistration.system.domain.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitService {

    List<Visit> findBetween(LocalDateTime from, LocalDateTime to);

    List<Visit> findBetweenByDoctor(LocalDateTime from, LocalDateTime to, Long idUser);

    List<Visit> findAllHistoricalByIdUser(Long idUser);

    List<Visit> findAllActualByIdUser(Long idUser);

    Visit findByVisitId(Long idVisit);

    List<Visit> saveAll(List<Visit> visits);

    Visit bookVisit(Long idVisit, Long idUser);

    Visit confirmVisit(Long idVisit, String description);

    Visit move(Visit data);

    void delete(Long idVisit);

}
