package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitRepository;
import com.patientregistration.system.service.UserService;
import com.patientregistration.system.service.VisitService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;
    private UserService userService;

    public VisitServiceImpl(VisitRepository visitRepository, UserService userService) {
        this.visitRepository = visitRepository;
        this.userService = userService;
    }

    @Override
    public List<Visit> findBetween(LocalDateTime from, LocalDateTime to) {
        return visitRepository.findBetween(from, to);
    }

    @Override
    public List<Visit> findAllVisitsByIdUser(Long idUser) {
        User user = userService.findUserById(idUser);
        return visitRepository.findAllByUser(user);
    }

    @Override
    public Visit findByVisitId(Long idVisit) {
        return visitRepository.findById(idVisit)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Visit with id: " + idVisit.toString()));
    }

    @Override
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit bookVisit(Long idVisit) {
        Visit visit = findByVisitId(idVisit);
        visit.setText("Zajęte");
        User user = userService.findUserById(1L);
        visit.setUser(user);
        return visitRepository.save(visit);
    }

    @Override
    public void delete(Long idVisit) {
        visitRepository.deleteById(idVisit);
    }

    // More accurate search

    @Override
    public List<Visit> getAllForWeek(Date startDate) {
        return visitRepository.getAllForWeek(startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorForWeek(Long idDoctor, Date startDate) {
        return visitRepository.findAllByIdDoctorForWeek(idDoctor, startDate);
    }

    @Override
    public List<Visit> findAllBySpecializationForWeek(String specialization, Date startDate) {
        return visitRepository.findAllBySpecializationForWeek(specialization.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByCityForWeek(String city, Date startDate) {
        return visitRepository.findAllByCityForWeek(city.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdClinicForWeek(Long idClinic, Date startDate) {
        return visitRepository.findAllByIdClinicForWeek(idClinic, startDate);
    }

    @Override
    public List<Visit> findAllByCareTypeForWeek(String careType, Date startDate) {
        return visitRepository.findAllByCareTypeForWeek(careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorBySpecializationForWeek(Long idDoctor, String specialization, Date startDate) {
        return visitRepository.findAllByIdDoctorBySpecializationForWeek(idDoctor, specialization.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorByCityForWeek(Long idDoctor, String city, Date startDate) {
        return visitRepository.findAllByIdDoctorByCityForWeek(idDoctor, city.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorByIdClinicForWeek(Long idDoctor, Long idClinic, Date startDate) {
        return visitRepository.findAllByIdDoctorByIdClinicForWeek(idDoctor, idClinic, startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorByCareTypeForWeek(Long idDoctor, String careType, Date startDate) {
        return visitRepository.findAllByIdDoctorByCareTypeForWeek(idDoctor, careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllBySpecializationByCityForWeek(String specialization, String city, Date startDate) {
        return visitRepository.findAllBySpecializationByCityForWeek(specialization.toLowerCase(), city.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllBySpecializationByIdClinicForWeek(String specialization, Long idClinic, Date startDate) {
        return visitRepository.findAllBySpecializationByIdClinicForWeek(specialization.toLowerCase(), idClinic, startDate);
    }

    @Override
    public List<Visit> findAllBySpecializationByCareTypeForWeek(String specialization, String careType, Date startDate) {
        return visitRepository.findAllBySpecializationByCareTypeForWeek(specialization.toLowerCase(), careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByCityByIdClinicForWeek(String city, Long idClinic, Date startDate) {
        return visitRepository.findAllByCityByIdClinicForWeek(city.toLowerCase(), idClinic, startDate);
    }

    @Override
    public List<Visit> findAllByCityByCareTypeForWeek(String city, String careType, Date startDate) {
        return visitRepository.findAllByCityByCareTypeForWeek(city.toLowerCase(), careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdClinicByCareTypeForWeek(Long idClinic, String careType, Date startDate) {
        return visitRepository.findAllByIdClinicByCareTypeForWeek(idClinic, careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorBySpecializationByCityForWeek(Long idDoctor, String specialization, String city, Date startDate) {
        return visitRepository.findAllByIdDoctorBySpecializationByCityForWeek(idDoctor, specialization.toLowerCase(), city.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorBySpecializationByIdClinicForWeek(Long idDoctor, String specialization, Long idClinic, Date startDate) {
        return visitRepository.findAllByIdDoctorBySpecializationByIdClinicForWeek(idDoctor, specialization.toLowerCase(), idClinic, startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorBySpecializationByCareTypeForWeek(Long idDoctor, String specialization, String careType, Date startDate) {
        return visitRepository.findAllByIdDoctorBySpecializationByCareTypeForWeek(idDoctor, specialization.toLowerCase(), careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorByCityByIdClinicForWeek(Long idDoctor, String city, Long idClinic, Date startDate) {
        return visitRepository.findAllByIdDoctorByCityByIdClinicForWeek(idDoctor, city.toLowerCase(), idClinic, startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorByCityByCareTypeForWeek(Long idDoctor, String city, String careType, Date startDate) {
        return visitRepository.findAllByIdDoctorByCityByCareTypeForWeek(idDoctor, city.toLowerCase(), careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorByIdClinicByCareTypeForWeek(Long idDoctor, Long idClinic, String careType, Date startDate) {
        return visitRepository.findAllByIdDoctorByIdClinicByCareTypeForWeek(idDoctor, idClinic, careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllBySpecializationByCityByIdClinicForWeek(String specialization, String city, Long idClinic, Date startDate) {
        return visitRepository.findAllBySpecializationByCityByIdClinicForWeek(specialization.toLowerCase(), city.toLowerCase(), idClinic, startDate);
    }

    @Override
    public List<Visit> findAllBySpecializationByCityByCareTypeForWeek(String specialization, String city, String careType, Date startDate) {
        return visitRepository.findAllBySpecializationByCityByCareTypeForWeek(specialization.toLowerCase(), city.toLowerCase(), careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllBySpecializationByIdClinicByCareTypeForWeek(String specialization, Long idClinic, String careType, Date startDate) {
        return visitRepository.findAllBySpecializationByIdClinicByCareTypeForWeek(specialization.toLowerCase(), idClinic, careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> getAllByCityByIdClinicByCareTypeForWeek(String city, Long idClinic, String careType, Date startDate) {
        return visitRepository.getAllByCityByIdClinicByCareTypeForWeek(city.toLowerCase(), idClinic, careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorBySpecializationByCityByIdClinicForWeek(Long idDoctor, String specialization, String city, Long idClinic, Date startDate) {
        return visitRepository.findAllByIdDoctorBySpecializationByCityByIdClinicForWeek(idDoctor, specialization.toLowerCase(), city.toLowerCase(), idClinic, startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorBySpecializationByCityByCareTypeForWeek(Long idDoctor, String specialization, String city, String careType, Date startDate) {
        return visitRepository.findAllByIdDoctorBySpecializationByCityByCareTypeForWeek(idDoctor, specialization.toLowerCase(), city.toLowerCase(), careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorBySpecializationByIdClinicByCareTypeForWeek(Long idDoctor, String specialization, Long idClinic, String careType, Date startDate) {
        return visitRepository.findAllByIdDoctorBySpecializationByIdClinicByCareTypeForWeek(idDoctor, specialization.toLowerCase(), idClinic, careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorByCityByIdClinicByCareTypeForWeek(Long idDoctor, String city, Long idClinic, String careType, Date startDate) {
        return visitRepository.findAllByIdDoctorByCityByIdClinicByCareTypeForWeek(idDoctor, city.toLowerCase(), idClinic, careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllBySpecializationByCityByIdClinicByCareTypeForWeek(String specialization, String city, Long idClinic, String careType, Date startDate) {
        return visitRepository.findAllBySpecializationByCityByIdClinicByCareTypeForWeek(specialization.toLowerCase(), city.toLowerCase(), idClinic, careType.toLowerCase(), startDate);
    }

    @Override
    public List<Visit> findAllByIdDoctorBySpecializationByCityByIdClinicByCareTypeForWeek(Long idDoctor, String specialization, String city, Long idClinic, String careType, Date startDate) {
        return visitRepository.findAllByIdDoctorBySpecializationByCityByIdClinicByCareTypeForWeek(idDoctor, specialization.toLowerCase(), city.toLowerCase(), idClinic, careType.toLowerCase(), startDate);
    }

}
