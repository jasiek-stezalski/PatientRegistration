package com.patientregistration.system.repository;

import com.patientregistration.system.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Long> {
}
