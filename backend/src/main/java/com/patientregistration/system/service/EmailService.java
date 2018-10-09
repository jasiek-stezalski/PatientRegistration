package com.patientregistration.system.service;

import com.patientregistration.system.domain.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailService {

    void cancelVisitEmail(List<Visit> visit);

    void moveVisitEmail(List<Visit> visits, List<LocalDateTime> term);
}
