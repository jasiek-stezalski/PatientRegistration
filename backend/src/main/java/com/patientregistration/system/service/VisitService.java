package com.patientregistration.system.service;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.domain.VisitModel;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitService {

    List<Visit> findBetweenByDoctor(LocalDateTime from, LocalDateTime to, Long idUser);

    List<Visit> findAllByFilterLimit5(String careType, String city, Long idClinic, String specialization);

    List<Visit> findAllByFilter(String careType, String city, Long idClinic, String specialization);

    List<Visit> findAllByVisitModelInNextMonth(VisitModel visitModel, Visit visit);

    List<Visit> findAllHistoricalByIdUser(Long idUser);

    List<Visit> findAllActualByIdUser(Long idUser);

    List<Visit> findAllActualByUser(Long idUser);

    List<Visit> findAllByIdUserAndDay(Long idUser, LocalDateTime day);

    Visit findByVisitId(Long idVisit);

    List<Visit> saveAll(List<Visit> visits);

    Visit bookVisit(Visit visit, Long idUser);

    Visit bookVisitByDoctor(Visit visit, Long idUser);

    Visit changeVisit(Visit newVisit, Long idOldVisit);

    Visit confirmVisit(Visit visit);

    Visit move(Visit visit);

    Visit cancel(Visit visit);

    void delete(Long idVisit);

    void dataUpdate();
}
