package com.patientregistration.system.repository;

import com.patientregistration.system.domain.VisitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitModelRepository extends JpaRepository<VisitModel, Long> {

    @Query("from VisitModel vm where not(vm.end < :from and vm.start > :to)")
    List<VisitModel> findBetween(@Param("from") LocalDateTime start, @Param("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

}
