package com.patientregistration.system.service;

import com.patientregistration.system.domain.Visit;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitService {

    List<Visit> findBetween(LocalDateTime from, LocalDateTime to);

    List<Visit> findAllVisitsByIdUser(Long idUser);

    Visit findByVisitId(Long idVisit);

    Visit save(Visit visit);

    Visit bookVisit(Long idVisit, Long idUser);

    void delete(Long idVisit);

    // More accurate search

    List<Visit> getAllForWeek(Date startDate);

    List<Visit> findAllByIdDoctorForWeek(Long idDoctor, Date startDate);

    List<Visit> findAllBySpecializationForWeek(String specialization, Date startDate);

    List<Visit> findAllByCityForWeek(String city, Date startDate);

    List<Visit> findAllByIdClinicForWeek(Long idClinic, Date startDate);

    List<Visit> findAllByCareTypeForWeek(String careType, Date startDate);

    List<Visit> findAllByIdDoctorBySpecializationForWeek(Long idDoctor, String specialization, Date startDate);

    List<Visit> findAllByIdDoctorByCityForWeek(Long idDoctor, String city, Date startDate);

    List<Visit> findAllByIdDoctorByIdClinicForWeek(Long idDoctor, Long idClinic, Date startDate);

    List<Visit> findAllByIdDoctorByCareTypeForWeek(Long idDoctor, String careType, Date startDate);

    List<Visit> findAllBySpecializationByCityForWeek(String specialization, String city, Date startDate);

    List<Visit> findAllBySpecializationByIdClinicForWeek(String specialization, Long idClinic, Date startDate);

    List<Visit> findAllBySpecializationByCareTypeForWeek(String specialization, String careType, Date startDate);

    List<Visit> findAllByCityByIdClinicForWeek(String city, Long idClinic, Date startDate);

    List<Visit> findAllByCityByCareTypeForWeek(String city, String careType, Date startDate);

    List<Visit> findAllByIdClinicByCareTypeForWeek(Long idClinic, String careType, Date startDate);

    List<Visit> findAllByIdDoctorBySpecializationByCityForWeek(Long idDoctor, String specialization, String city, Date startDate);

    List<Visit> findAllByIdDoctorBySpecializationByIdClinicForWeek(Long idDoctor, String specialization, Long idClinic, Date startDate);

    List<Visit> findAllByIdDoctorBySpecializationByCareTypeForWeek(Long idDoctor, String specialization, String careType, Date startDate);

    List<Visit> findAllByIdDoctorByCityByIdClinicForWeek(Long idDoctor, String city, Long idClinic, Date startDate);

    List<Visit> findAllByIdDoctorByCityByCareTypeForWeek(Long idDoctor, String city, String careType, Date startDate);

    List<Visit> findAllByIdDoctorByIdClinicByCareTypeForWeek(Long idDoctor, Long idClinic, String careType, Date startDate);

    List<Visit> findAllBySpecializationByCityByIdClinicForWeek(String specialization, String city, Long idClinic, Date startDate);

    List<Visit> findAllBySpecializationByCityByCareTypeForWeek(String specialization, String city, String careType, Date startDate);

    List<Visit> findAllBySpecializationByIdClinicByCareTypeForWeek(String specialization, Long idClinic, String careType, Date startDate);

    List<Visit> getAllByCityByIdClinicByCareTypeForWeek(String city, Long idClinic, String careType, Date startDate);

    List<Visit> findAllByIdDoctorBySpecializationByCityByIdClinicForWeek(Long idDoctor, String specialization, String city, Long idClinic, Date startDate);

    List<Visit> findAllByIdDoctorBySpecializationByCityByCareTypeForWeek(Long idDoctor, String specialization, String city, String careType, Date startDate);

    List<Visit> findAllByIdDoctorBySpecializationByIdClinicByCareTypeForWeek(Long idDoctor, String specialization, Long idClinic, String careType, Date startDate);

    List<Visit> findAllByIdDoctorByCityByIdClinicByCareTypeForWeek(Long idDoctor, String city, Long idClinic, String careType, Date startDate);

    List<Visit> findAllBySpecializationByCityByIdClinicByCareTypeForWeek(String specialization, String city, Long idClinic, String careType, Date startDate);

    List<Visit> findAllByIdDoctorBySpecializationByCityByIdClinicByCareTypeForWeek(Long idDoctor, String specialization, String city, Long idClinic, String careType, Date startDate);

}
