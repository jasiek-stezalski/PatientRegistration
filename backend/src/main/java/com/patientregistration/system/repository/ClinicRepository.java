package com.patientregistration.system.repository;

import com.patientregistration.system.domain.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    @Query(nativeQuery = true,
            value = "SELECT c.* " +
                    "FROM clinic c " +
                    "RIGHT JOIN doctor_clinics dc ON c.id = dc.id_clinic " +
                    "WHERE dc.id_doctor =:idUser")
    List<Clinic> findAllByIdUser(@Param("idUser") Long idUser);
}
