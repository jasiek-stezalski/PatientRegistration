package com.patientregistration.system.service;

import com.patientregistration.system.domain.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitService {

    List<Visit> findBetweenByDoctor(LocalDateTime from, LocalDateTime to, Long idUser);

    List<Visit> findAllByFilterLimit5(String careType, String city, Long idClinic, String specialization);

    List<Visit> findAllByFilter(String careType, String city, Long idClinic, String specialization);

    List<Visit> findAllHistoricalByIdUser(Long idUser);

    List<Visit> findAllActualByIdUser(Long idUser);

    List<Visit> findAllActualByUser(Long idUser);

    Visit findByVisitId(Long idVisit);

    List<Visit> saveAll(List<Visit> visits);

    Visit bookVisit(Visit visit, Long idUser);

    Visit confirmVisit(Visit visit);

    Visit move(Visit visit);

    Visit cancel(Visit visit);

    void delete(Long idVisit);
}
