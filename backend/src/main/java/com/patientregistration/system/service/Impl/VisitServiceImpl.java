package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitRepository;
import com.patientregistration.system.service.EmailService;
import com.patientregistration.system.service.UserService;
import com.patientregistration.system.service.VisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {

    private VisitRepository visitRepository;
    private UserService userService;
    private EmailService emailService;

    public VisitServiceImpl(VisitRepository visitRepository, UserService userService, EmailService emailService) {
        this.visitRepository = visitRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public List<Visit> findBetweenByDoctor(LocalDateTime from, LocalDateTime to, Long idUser) {
        List<Visit> visits = visitRepository.findBetween(from, to);
        return visits
                .stream()
                .filter(v -> v.getVisitModel().getUser().getId().equals(idUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<Visit> findAllByFilterLimit5(String careType, String city, Long idClinic, String specialization) {
        if (idClinic == -1)
            return visitRepository.findAllByCareTypeAndCityAndSpecializationLimit5("Zajęte", "Zakończone",
                    careType, city, specialization);
        else
            return visitRepository.findAllByCareTypeAndCityAndClinicAndSpecializationLimit5("Zajęte", "Zakończone",
                    careType, city, idClinic, specialization);
    }

    @Override
    public List<Visit> findAllByFilter(String careType, String city, Long idClinic, String specialization) {
        return visitRepository.findAllByCareTypeAndCityAndClinicAndSpecialization("Zajęte", "Zakończone",
                careType, city, idClinic, specialization);
    }

    @Override
    public List<Visit> findAllHistoricalByIdUser(Long idUser) {
        LocalDate today = LocalDate.now();
        return visitRepository.findAllByUserAndStartBeforeOrderByStartDesc(idUser, today.atStartOfDay(), "Zakończone");
    }

    @Override
    public List<Visit> findAllActualByIdUser(Long idUser) {
        LocalDate today = LocalDate.now();
        return visitRepository.findAllByUserAndStartAfterOrderByStartDesc(idUser, today.atStartOfDay(), "Zajęte");
    }

    @Override
    public Visit findByVisitId(Long idVisit) {
        return visitRepository.findById(idVisit)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Visit with id: " + idVisit.toString()));
    }

    @Override
    public List<Visit> saveAll(List<Visit> visits) {
        return visitRepository.saveAll(visits);
    }

    @Override
    @Transactional
    public Visit bookVisit(Visit data, Long idUser) {
        Visit visit = findByVisitId(data.getId());
        visit.setText("Zajęte");
        User user = userService.findUserById(idUser);
        visit.setUser(user);
        Visit save = visitRepository.save(visit);
        emailService.bookVisitEmail(visit);
        return save;
    }

    @Override
    public Visit confirmVisit(Visit data) {
        Visit visit = findByVisitId(data.getId());
        visit.setText("Zakończone");
        visit.setDescription(data.getDescription());
        if (data.getDescription() == null)
            visit.setDescription("-");
        return visitRepository.save(visit);
    }

    @Override
    public Visit move(Visit data) {
        Visit visit = findByVisitId(data.getId());
        LocalDateTime term = visit.getStart();
        visit.setEnd(data.getEnd());
        visit.setStart(data.getStart());
        Visit savedVisit = visitRepository.save(visit);
        emailService.moveVisitEmail(Collections.singletonList(visit), Collections.singletonList(term));
        return savedVisit;
    }

    @Override
    public void delete(Long idVisit) {
        emailService.cancelVisitEmail(Collections.singletonList(findByVisitId(idVisit)));
        visitRepository.deleteById(idVisit);
    }

}
