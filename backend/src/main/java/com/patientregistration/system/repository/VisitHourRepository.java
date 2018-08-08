package com.patientregistration.system.repository;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.VisitHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitHourRepository extends JpaRepository<VisitHour, Long> {

    List<VisitHour> findAllByUser(User user);

}
