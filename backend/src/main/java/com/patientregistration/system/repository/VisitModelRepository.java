package com.patientregistration.system.repository;

import com.patientregistration.system.domain.VisitModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitModelRepository extends JpaRepository<VisitModel, Long> {
}
