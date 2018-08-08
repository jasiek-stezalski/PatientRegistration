package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.VisitHour;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitHourRepository;
import com.patientregistration.system.service.UserService;
import com.patientregistration.system.service.VisitHourService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitHourServiceImpl implements VisitHourService {

    private VisitHourRepository visitHourRepository;
    private UserService userService;

    public VisitHourServiceImpl(VisitHourRepository visitHourRepository, UserService userService) {
        this.visitHourRepository = visitHourRepository;
        this.userService = userService;
    }

    @Override
    public List<VisitHour> findAllVisitHours() {
        return visitHourRepository.findAll();
    }

    @Override
    public VisitHour findByVisitHourById(Long idVisitHour) {
        return visitHourRepository.findById(idVisitHour)
                .orElseThrow(() -> new ResourceNotFoundException(idVisitHour.toString()));
    }

    @Override
    public VisitHour saveOrUpdate(VisitHour visitHour) {
        return visitHourRepository.save(visitHour);
    }

    @Override
    public void delete(Long idVisitHour) {
        visitHourRepository.deleteById(idVisitHour);
    }

    @Override
    public List<VisitHour> findAllVisitHoursByIdUser(Long idUser) {
        User user = userService.findUserById(idUser);
        return visitHourRepository.findAllByUser(user);
    }

}
