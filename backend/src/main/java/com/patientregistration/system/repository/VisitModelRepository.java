package com.patientregistration.system.repository;

import com.patientregistration.system.domain.VisitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitModelRepository extends JpaRepository<VisitModel, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM visit_model WHERE visit_model.id_doctor =:idDoctor AND visit_model.end_date >= CURRENT_DATE")
    List<VisitModel> findAllByUser(@Param("idDoctor") Long idDoctor);

    @Query(nativeQuery = true,
            value = "SELECT * FROM visit_model WHERE visit_model.specialization =:specialization AND visit_model.end_date >= CURRENT_DATE")
    List<VisitModel> findAllBySpecialization(@Param("specialization") String specialization);
}
