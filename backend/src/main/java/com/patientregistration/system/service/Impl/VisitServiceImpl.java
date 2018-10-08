package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitRepository;
import com.patientregistration.system.service.UserService;
import com.patientregistration.system.service.VisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Visit> findBetweenByDoctor(LocalDateTime from, LocalDateTime to, Long idUser) {
        List<Visit> visits = visitRepository.findBetween(from, to);
        return visits
                .stream()
                .filter(v -> v.getVisitModel().getUser().getId().equals(idUser))
                .collect(Collectors.toList());
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
    @Transactional
    public Visit bookVisit(Long idVisit, Long idUser) {
        Visit visit = findByVisitId(idVisit);
        visit.setText("Zajęte");
        User user = userService.findUserById(idUser);
        visit.setUser(user);
        return visitRepository.save(visit);
    }

    @Override
    public void delete(Long idVisit) {
        visitRepository.deleteById(idVisit);
    }

    @Override
    public List<Visit> findAllByVisitModel(VisitModel visitModel) {
        return visitRepository.findAllByVisitModel(visitModel);
    }

}
