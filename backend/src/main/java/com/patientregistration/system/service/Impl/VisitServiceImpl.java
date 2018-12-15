package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.Person;
import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.exception.DataConflictException;
import com.patientregistration.system.exception.DataNotAcceptableException;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.PersonRepository;
import com.patientregistration.system.repository.VisitRepository;
import com.patientregistration.system.service.EmailService;
import com.patientregistration.system.service.UserService;
import com.patientregistration.system.service.VisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private VisitRepository visitRepository;
    private UserService userService;
    private PersonRepository personRepository;
    private EmailService emailService;

    public VisitServiceImpl(VisitRepository visitRepository, UserService userService, PersonRepository personRepository, EmailService emailService) {
        this.visitRepository = visitRepository;
        this.userService = userService;
        this.personRepository = personRepository;
        this.emailService = emailService;
    }

    @Override
    public List<Visit> findAllByDoctor(Long idDoctor) {

        return visitRepository.findAllByDoctor(idDoctor);

    }

    @Override
    public List<Visit> findAllByDoctorInThisTerm(Long idUser, LocalDateTime startTerm, LocalDateTime endTerm) {
        return visitRepository.findAllByDoctorInThisTerm(idUser, startTerm, endTerm);
    }

    @Override
    public List<Visit> findAllByDoctorInThisTermFromOtherModel(Long idDoctor, LocalDateTime startTerm, LocalDateTime endTerm, Long idVisitModel) {
        return visitRepository.findAllByDoctorInThisTermFromOtherModel(idDoctor, startTerm, endTerm, idVisitModel);
    }

    private List<Visit> findAllByDoctorInThisTermFromOtherVisit(Long idDoctor, LocalDateTime startTerm, LocalDateTime endTerm, Long idVisit) {
        return visitRepository.findAllByDoctorInThisTermFromOtherVisit(idDoctor, startTerm, endTerm, idVisit);
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
    public List<Visit> findAllByFilter(String careType, String city, Long idClinic, String specialization) {
        return visitRepository.findAllByCareTypeAndCityAndClinicAndSpecialization(careType, city, idClinic, specialization);
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
    public List<Visit> findAllByFilterMonthly(String careType, String city, Long idClinic, String specialization, Integer month) {
        return visitRepository.findAllByCareTypeAndCityAndClinicAndSpecializationMonthly("Zajęte", "Zakończone", careType, city, idClinic, specialization, LocalDateTime.now().plusMonths(month));
    }

    @Override
    public List<Visit> findAllByVisitModelInNextMonth(VisitModel visitModel, Visit visit) {
        return visitRepository.findAllByVisitModelAndStartAfterAndStartBefore(visitModel.getId(), "Zajęte", visit.getStart(), visit.getStart().plusMonths(1));
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
    public List<Visit> findAllActualByUser(Long idUser) {
        return visitRepository.findAllByStatusAndUser("Zajęte", idUser);
    }

    @Override
    public List<Visit> findAllByIdUserAndDay(Long idUser, LocalDateTime day) {
        return visitRepository.findAllByUserAndStartAfterAndStartBefore(userService.findUserById(idUser), day.toLocalDate().atTime(0, 0), day.toLocalDate().plusDays(1).atTime(0, 0));
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

        checkInsurance(idUser, visit);

        List<Visit> theSameVisits = findAllActualByUser(idUser)
                .stream()
                .filter(v -> v.getVisitModel().getUser().getSpecialization().equals(data.getVisitModel().getUser().getSpecialization()))
                .collect(Collectors.toList());

        if (theSameVisits.isEmpty()) {
            Visit newVisit = checkAvailableTerm(idUser, visit);
            emailService.bookVisitEmail(newVisit);
            return newVisit;
        } else throw new DataConflictException("You cannot book another visit to the same specialist!");

    }

    @Override
    public Visit bookVisitByDoctor(Visit data, Long idUser) {
        Visit visit = findByVisitId(data.getId());

        checkInsurance(idUser, visit);

        Visit newVisit = checkAvailableTerm(idUser, visit);
        emailService.bookVisitEmail(newVisit);
        return newVisit;
    }

    @Override
    @Transactional
    public Visit changeVisit(Visit newVisit, Long idOldVisit) {
        Visit oldVisit = findByVisitId(idOldVisit);

        Visit visit = findByVisitId(newVisit.getId());

        Visit newSavedVisit = checkAvailableTerm(oldVisit.getUser().getId(), visit);

        emailService.freeSlotVisitEmail(oldVisit, findAllByVisitModelInNextMonth(oldVisit.getVisitModel(), oldVisit), oldVisit.getUser());

        oldVisit.setUser(null);
        oldVisit.setText(oldVisit.getStart().toString().substring(11, 16));
        visitRepository.save(oldVisit);

        emailService.changeVisitEmail(newSavedVisit);
        return newSavedVisit;
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
    @Transactional
    public Visit move(Visit data) {
        Visit visit = findByVisitId(data.getId());
        LocalDateTime term = visit.getStart();
        visit.setEnd(LocalDateTime.parse(data.getEnd().toLocalDate() + " " + data.getEnd().toLocalTime(), FORMATTER));
        visit.setStart(LocalDateTime.parse(data.getStart().toLocalDate() + " " + data.getStart().toLocalTime(), FORMATTER));
        visit.setText(visit.getStart().toLocalTime().toString());

        List<Visit> visitsInThisTerm = findAllByDoctorInThisTermFromOtherVisit(visit.getVisitModel().getUser().getId(), visit.getStart(), visit.getEnd(), visit.getId());

        if (!visitsInThisTerm.isEmpty())
            throw new DataConflictException("You cannot have two visits at once");

        if (visit.getUser() != null)
            emailService.moveVisitEmail(Collections.singletonList(visit), Collections.singletonList(term));
        return visitRepository.save(visit);
    }


    @Override
    @Transactional
    public Visit cancel(Visit data) {
        Visit visit = findByVisitId(data.getId());

        if (visit.getUser() != null)
            emailService.cancelVisitEmail(visit);
        emailService.freeSlotVisitEmail(visit, findAllByVisitModelInNextMonth(visit.getVisitModel(), visit), visit.getUser());

        visit.setUser(null);
        visit.setText(visit.getStart().toLocalTime().toString());

        return visitRepository.save(visit);
    }

    @Override
    public void delete(Long idVisit) {
        Visit visit = findByVisitId(idVisit);
        if (visit.getUser() != null)
            emailService.cancelVisitEmail(visit);
        visitRepository.deleteById(idVisit);
    }

    @Override
    @Transactional
    public void dataUpdate() {
        List<Visit> visits = visitRepository.findAll();
        for (Visit visit : visits) {
            if (!visit.getText().equals("Zakończone") && visit.getStart().isBefore(LocalDate.now().atStartOfDay())) {
                visit.setText("Zakończone");
            }
        }
        visitRepository.saveAll(visits);
    }

    private Visit checkAvailableTerm(Long idUser, Visit visit) {

        List<Visit> existingVisits = visitRepository.findAllByUserAndStartAfterAndEndBefore(userService.findUserById(idUser).getId(), visit.getStart(), visit.getEnd());

        if (existingVisits.isEmpty()) {
            visit.setText("Zajęte");
            User user = userService.findUserById(idUser);
            visit.setUser(user);
            return visitRepository.save(visit);
        } else throw new DataNotAcceptableException("You cannot have two visits at once");
    }

    private void checkInsurance(Long idUser, Visit visit) {
        if (visit.getVisitModel().getCareType().equals("Publiczna")) {
            User user = userService.findUserById(idUser);
            Person person = personRepository.findPersonByPesel(user.getPesel());

            if (!person.getInsurance())
                throw new ResourceNotFoundException("User doesn't have insurance!");
        }
    }

}
