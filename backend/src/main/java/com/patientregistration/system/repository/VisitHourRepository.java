package com.patientregistration.system.repository;

import com.patientregistration.system.domain.VisitHour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitHourRepository extends JpaRepository<VisitHour, Long> {
}
