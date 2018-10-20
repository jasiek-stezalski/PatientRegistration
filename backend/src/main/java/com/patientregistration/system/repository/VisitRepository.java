package com.patientregistration.system.repository;

import com.patientregistration.system.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("from Visit v where not(v.start < :from and v.start > :to)")
    List<Visit> findBetween(@Param("from") LocalDateTime start,
                            @Param("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "WHERE v.id_user =:idUser " +
                    "AND (v.start < :now OR v.status = :status) " +
                    "ORDER BY v.start DESC")
    List<Visit> findAllByUserAndStartBeforeOrderByStartDesc(@Param("idUser") Long idUser,
                                                            @Param("now") LocalDateTime now,
                                                            @Param("status") String status);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "WHERE v.id_user =:idUser " +
                    "AND v.start > :now " +
                    "AND v.status = :status " +
                    "ORDER BY v.start")
    List<Visit> findAllByUserAndStartAfterOrderByStartDesc(@Param("idUser") Long idUser,
                                                           @Param("now") LocalDateTime now,
                                                           @Param("status") String status);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "JOIN visit_model vm ON v.id_visit_model = vm.id " +
                    "JOIN clinic c ON vm.id_clinic = c.id " +
                    "WHERE v.status != :status1 " +
                    "AND v.status != :status2 " +
                    "AND vm.care_type =:careType " +
                    "AND c.city =:city " +
                    "AND vm.specialization =:specialization " +
                    "ORDER BY v.start " +
                    "LIMIT 5")
    List<Visit> findAllByCareTypeAndCityAndSpecializationLimit5(@Param("status1") String status1,
                                                                @Param("status2") String status2,
                                                                @Param("careType") String careType,
                                                                @Param("city") String city,
                                                                @Param("specialization") String specialization);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "JOIN visit_model vm ON v.id_visit_model = vm.id " +
                    "JOIN clinic c ON vm.id_clinic = c.id " +
                    "WHERE v.status != :status1 " +
                    "AND v.status != :status2 " +
                    "AND vm.care_type =:careType " +
                    "AND c.city =:city " +
                    "AND vm.specialization =:specialization " +
                    "ORDER BY v.start ")
    List<Visit> findAllByCareTypeAndCityAndSpecialization(@Param("status1") String status1,
                                                          @Param("status2") String status2,
                                                          @Param("careType") String careType,
                                                          @Param("city") String city,
                                                          @Param("specialization") String specialization);


}
