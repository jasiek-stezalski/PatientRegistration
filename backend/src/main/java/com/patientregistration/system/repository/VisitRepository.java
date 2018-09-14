package com.patientregistration.system.repository;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("from Visit v where not(v.term < :from and v.term > :to)")
    List<Visit> findBetween(@Param("from") LocalDateTime start, @Param("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    List<Visit> findAllByUser(User user);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "WHERE v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> getAllForWeek(@Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorForWeek(@Param(value = "idDoctor") Long idDoctor, @Param(value = "startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE LOWER(vm.specialization) =:specialization " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllBySpecializationForWeek(@Param("specialization") String specialization, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE LOWER(c.city) =:city " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByCityForWeek(@Param(value = "city") String city, @Param(value = "startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_clinic =:idClinic " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdClinicForWeek(@Param(value = "id") Long idClinic, @Param(value = "startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByCareTypeForWeek(@Param(value = "careType") String careType, @Param(value = "startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.specialization) =:specialization " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorBySpecializationForWeek(@Param(value = "idDoctor") Long idDoctor, @Param("specialization") String specialization, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(c.city) =:city " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorByCityForWeek(@Param(value = "idDoctor") Long idDoctor, @Param(value = "city") String city, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorByIdClinicForWeek(@Param(value = "idDoctor") Long idDoctor, @Param(value = "id") Long idClinic, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorByCareTypeForWeek(@Param(value = "idDoctor") Long idDoctor, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(c.city) =:city " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllBySpecializationByCityForWeek(@Param("specialization") String specialization, @Param(value = "city") String city, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE LOWER(vm.specialization) =:specialization " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllBySpecializationByIdClinicForWeek(@Param("specialization") String specialization, @Param(value = "id") Long idClinic, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllBySpecializationByCareTypeForWeek(@Param("specialization") String specialization, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE LOWER(c.city) =:city " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByCityByIdClinicForWeek(@Param(value = "city") String city, @Param(value = "id") Long idClinic, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE LOWER(c.city) =:city " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByCityByCareTypeForWeek(@Param(value = "city") String city, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_clinic =:idClinic " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdClinicByCareTypeForWeek(@Param(value = "id") Long idClinic, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(c.city) =:city " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorBySpecializationByCityForWeek(@Param(value = "idDoctor") Long idDoctor, @Param("specialization") String specialization, @Param(value = "city") String city, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.specialization) =:specialization " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorBySpecializationByIdClinicForWeek(@Param(value = "idDoctor") Long idDoctor, @Param("specialization") String specialization, @Param(value = "id") Long idClinic, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorBySpecializationByCareTypeForWeek(@Param(value = "idDoctor") Long idDoctor, @Param("specialization") String specialization, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(c.city) =:city " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorByCityByIdClinicForWeek(@Param(value = "idDoctor") Long idDoctor, @Param(value = "city") String city, @Param(value = "id") Long idClinic, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(c.city) =:city " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorByCityByCareTypeForWeek(@Param(value = "idDoctor") Long idDoctor, @Param(value = "city") String city, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorByIdClinicByCareTypeForWeek(@Param(value = "idDoctor") Long idDoctor, @Param(value = "id") Long idClinic, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(c.city) =:city " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllBySpecializationByCityByIdClinicForWeek(@Param("specialization") String specialization, @Param(value = "city") String city, @Param(value = "id") Long idClinic, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(c.city) =:city " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllBySpecializationByCityByCareTypeForWeek(@Param("specialization") String specialization, @Param(value = "city") String city, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE LOWER(vm.specialization) =:specialization " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllBySpecializationByIdClinicByCareTypeForWeek(@Param("specialization") String specialization, @Param(value = "id") Long idClinic, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE LOWER(c.city) =:city " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> getAllByCityByIdClinicByCareTypeForWeek(@Param(value = "city") String city, @Param(value = "id") Long idClinic, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(c.city) =:city " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorBySpecializationByCityByIdClinicForWeek(@Param(value = "idDoctor") Long idDoctor, @Param("specialization") String specialization, @Param(value = "city") String city, @Param(value = "id") Long idClinic, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(c.city) =:city " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorBySpecializationByCityByCareTypeForWeek(@Param(value = "idDoctor") Long idDoctor, @Param("specialization") String specialization, @Param(value = "city") String city, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.specialization) =:specialization " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorBySpecializationByIdClinicByCareTypeForWeek(@Param(value = "idDoctor") Long idDoctor, @Param("specialization") String specialization, @Param(value = "id") Long idClinic, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(c.city) =:city " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorByCityByIdClinicByCareTypeForWeek(@Param(value = "idDoctor") Long idDoctor, @Param(value = "city") String city, @Param(value = "id") Long idClinic, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(c.city) =:city " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllBySpecializationByCityByIdClinicByCareTypeForWeek(@Param("specialization") String specialization, @Param(value = "city") String city, @Param(value = "id") Long idClinic, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

    @Query(nativeQuery = true,
            value = "SELECT v.* " +
                    "FROM visit v " +
                    "RIGHT JOIN visit_model vm ON v.id_visit_model = vm.id_visit_model " +
                    "RIGHT JOIN clinic c ON vm.id_clinic = c.id_clinic " +
                    "WHERE vm.id_doctor =:idDoctor " +
                    "AND LOWER(vm.specialization) =:specialization " +
                    "AND LOWER(c.city) =:city " +
                    "AND vm.id_clinic =:idClinic " +
                    "AND LOWER(vm.care_type) =:careType " +
                    "AND v.visit_date >= :startDate " +
                    "AND v.visit_date < CAST(:startDate AS DATE) + INTERVAL '7' DAY")
    List<Visit> findAllByIdDoctorBySpecializationByCityByIdClinicByCareTypeForWeek(@Param(value = "idDoctor") Long idDoctor, @Param("specialization") String specialization, @Param(value = "city") String city, @Param(value = "id") Long idClinic, @Param(value = "careType") String careType, @Param("startDate") Date startDate);

}
