package com.patientregistration.system.repository;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("from Visit v where not(v.start < :from and v.start > :to)")
    List<Visit> findBetween(@Param("from") LocalDateTime start, @Param("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    List<Visit> findAllByUserAndStartBeforeOrderByStartDesc(User user, LocalDateTime start);

    List<Visit> findAllByUserAndStartAfterOrderByStartDesc(User user, LocalDateTime now);
}
