package com.patientregistration.system.service;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailService {

    void cancelVisitsEmail(List<Visit> visit);

    void cancelVisitEmail(Visit visit);

    void moveVisitEmail(List<Visit> visits, List<LocalDateTime> term);

    void bookVisitEmail(Visit visit);

    void freeSlotVisitEmail(Visit visit, List<Visit> allByVisitModelInNextMonth, User user);

    void changeVisitEmail(Visit visit);
}
