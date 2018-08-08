package com.patientregistration.system.controller;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.service.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
public class VisitController {

    private VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/visits")
    public List<Visit> getAllVisits() {
        return visitService.findAllVisits();
    }

    @GetMapping("/visits/{id}")
    public Visit getVisitById(@PathVariable(value = "id") Long idVisit) {
        return visitService.findByVisitId(idVisit);
    }

    @PostMapping("/visits")
    public Visit createVisit(@Valid @RequestBody Visit visit) {
        return visitService.save(visit);
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<?> deleteVisit(@PathVariable(value = "id") Long idVisit) {
        visitService.delete(idVisit);

        return ResponseEntity.ok().build();
    }

    // More accurate search

    @GetMapping("/visits/date")
    public List<Visit> getAllVisitsForWeek(@RequestParam Date startDate) {
        return visitService.getAllForWeek(startDate);
    }

    @GetMapping("/visits/doctor/{id}")
    public List<Visit> getAllVisitsByDoctorIdForWeek(@PathVariable(value = "id") Long idDoctor,
                                                     @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorForWeek(idDoctor, startDate);
    }

    @GetMapping("/visits/specialization")
    public List<Visit> getAllVisitsBySpecializationForWeek(@RequestParam String specialization,
                                                           @RequestParam Date startDate) {
        return visitService.findAllBySpecializationForWeek(specialization, startDate);
    }

    @GetMapping("/visits/city")
    public List<Visit> getAllVisitsByCityForWeek(@RequestParam String city,
                                                 @RequestParam Date startDate) {
        return visitService.findAllByCityForWeek(city, startDate);
    }

    @GetMapping("/visits/clinic/{id}")
    public List<Visit> getAllVisitsByIdClinicForWeek(@PathVariable(value = "id") Long idClinic,
                                                     @RequestParam Date startDate) {
        return visitService.findAllByIdClinicForWeek(idClinic, startDate);
    }

    @GetMapping("/visits/careType")
    public List<Visit> getAllVisitsByCareTypeForWeek(@RequestParam String careType,
                                                     @RequestParam Date startDate) {
        return visitService.findAllByCareTypeForWeek(careType, startDate);
    }

    @GetMapping("/visits/doctorAndSpecialization")
    public List<Visit> getAllVisitsByIdDoctorBySpecializationForWeek(@RequestParam Long idDoctor,
                                                                     @RequestParam String specialization,
                                                                     @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorBySpecializationForWeek(idDoctor, specialization, startDate);
    }

    @GetMapping("/visits/doctorAndCity")
    public List<Visit> getAllVisitsByIdDoctorByCityForWeek(@RequestParam Long idDoctor,
                                                           @RequestParam String city,
                                                           @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorByCityForWeek(idDoctor, city, startDate);
    }

    @GetMapping("/visits/doctorAndClinic")
    public List<Visit> getAllVisitsByIdDoctorByClinicForWeek(@RequestParam Long idDoctor,
                                                             @RequestParam Long idClinic,
                                                             @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorByIdClinicForWeek(idDoctor, idClinic, startDate);
    }

    @GetMapping("/visits/doctorAndCareType")
    public List<Visit> getAllVisitsByIdDoctorByCareTypeForWeek(@RequestParam Long idDoctor,
                                                               @RequestParam String careType,
                                                               @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorByCareTypeForWeek(idDoctor, careType, startDate);
    }

    @GetMapping("/visits/specializationAndCity")
    public List<Visit> getAllVisitsBySpecializationByCityForWeek(@RequestParam String specialization,
                                                                 @RequestParam String city,
                                                                 @RequestParam Date startDate) {
        return visitService.findAllBySpecializationByCityForWeek(specialization, city, startDate);
    }

    @GetMapping("/visits/specializationAndClinic")
    public List<Visit> getAllVisitsBySpecializationByIdClinicForWeek(@RequestParam String specialization,
                                                                     @RequestParam Long idClinic,
                                                                     @RequestParam Date startDate) {
        return visitService.findAllBySpecializationByIdClinicForWeek(specialization, idClinic, startDate);
    }

    @GetMapping("/visits/specializationAndCareType")
    public List<Visit> getAllVisitsBySpecializationByCareTypeForWeek(@RequestParam String specialization,
                                                                     @RequestParam String careType,
                                                                     @RequestParam Date startDate) {
        return visitService.findAllBySpecializationByCareTypeForWeek(specialization, careType, startDate);
    }

    @GetMapping("/visits/cityAndClinic")
    public List<Visit> getAllVisitsByCityByIdClinicForWeek(@RequestParam String city,
                                                           @RequestParam Long idClinic,
                                                           @RequestParam Date startDate) {
        return visitService.findAllByCityByIdClinicForWeek(city, idClinic, startDate);
    }

    @GetMapping("/visits/cityAndCareType")
    public List<Visit> getAllVisitsByCityByCareTypeForWeek(@RequestParam String city,
                                                           @RequestParam String careType,
                                                           @RequestParam Date startDate) {
        return visitService.findAllByCityByCareTypeForWeek(city, careType, startDate);
    }

    @GetMapping("/visits/clinicAndCareType")
    public List<Visit> getAllVisitsByClinicByCareTypeForWeek(@RequestParam Long idClinic,
                                                             @RequestParam String careType,
                                                             @RequestParam Date startDate) {
        return visitService.findAllByIdClinicByCareTypeForWeek(idClinic, careType, startDate);
    }

    @GetMapping("/visits/doctorAndSpecializationAndCity")
    public List<Visit> getAllVisitsByIdDoctorBySpecializationByCityForWeek(@RequestParam Long idDoctor,
                                                                           @RequestParam String specialization,
                                                                           @RequestParam String city,
                                                                           @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorBySpecializationByCityForWeek(idDoctor, specialization, city, startDate);
    }

    @GetMapping("/visits/doctorAndSpecializationAndClinic")
    public List<Visit> getAllVisitsByIdDoctorBySpecializationByClinicForWeek(@RequestParam Long idDoctor,
                                                                             @RequestParam String specialization,
                                                                             @RequestParam Long idClinic,
                                                                             @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorBySpecializationByIdClinicForWeek(idDoctor, specialization, idClinic, startDate);
    }

    @GetMapping("/visits/doctorAndSpecializationAndCareType")
    public List<Visit> getAllVisitsByIdDoctorBySpecializationByCareTypeForWeek(@RequestParam Long idDoctor,
                                                                               @RequestParam String specialization,
                                                                               @RequestParam String careType,
                                                                               @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorBySpecializationByCareTypeForWeek(idDoctor, specialization, careType, startDate);
    }

    @GetMapping("/visits/doctorAndCityAndClinic")
    public List<Visit> getAllVisitsByIdDoctorByCityByClinicForWeek(@RequestParam Long idDoctor,
                                                                   @RequestParam String city,
                                                                   @RequestParam Long idClinic,
                                                                   @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorByCityByIdClinicForWeek(idDoctor, city, idClinic, startDate);
    }

    @GetMapping("/visits/doctorAndCityAndCareType")
    public List<Visit> getAllVisitsByIdDoctorByCityByCareTypeForWeek(@RequestParam Long idDoctor,
                                                                     @RequestParam String city,
                                                                     @RequestParam String careType,
                                                                     @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorByCityByCareTypeForWeek(idDoctor, city, careType, startDate);
    }

    @GetMapping("/visits/doctorAndClinicAndCareType")
    public List<Visit> getAllVisitsByIdDoctorByClinicByCareTypeForWeek(@RequestParam Long idDoctor,
                                                                       @RequestParam Long idClinic,
                                                                       @RequestParam String careType,
                                                                       @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorByIdClinicByCareTypeForWeek(idDoctor, idClinic, careType, startDate);
    }

    @GetMapping("/visits/specializationAndCityAndClinic")
    public List<Visit> getAllVisitsBySpecializationByCityByClinicForWeek(@RequestParam String specialization,
                                                                         @RequestParam String city,
                                                                         @RequestParam Long idClinic,
                                                                         @RequestParam Date startDate) {
        return visitService.findAllBySpecializationByCityByIdClinicForWeek(specialization, city, idClinic, startDate);
    }

    @GetMapping("/visits/specializationAndCityAndCareType")
    public List<Visit> getAllVisitsBySpecializationByCityByCareTypeForWeek(@RequestParam String specialization,
                                                                           @RequestParam String city,
                                                                           @RequestParam String careType,
                                                                           @RequestParam Date startDate) {
        return visitService.findAllBySpecializationByCityByCareTypeForWeek(specialization, city, careType, startDate);
    }

    @GetMapping("/visits/specializationAndClinicAndCareType")
    public List<Visit> getAllVisitsBySpecializationByClinicByCareTypeForWeek(@RequestParam String specialization,
                                                                             @RequestParam Long idClinic,
                                                                             @RequestParam String careType,
                                                                             @RequestParam Date startDate) {
        return visitService.findAllBySpecializationByIdClinicByCareTypeForWeek(specialization, idClinic, careType, startDate);
    }

    @GetMapping("/visits/cityAndClinicAndCareType")
    public List<Visit> getAllVisitsByCityByClinicByCareTypeForWeek(@RequestParam String city,
                                                                   @RequestParam Long idClinic,
                                                                   @RequestParam String CareType,
                                                                   @RequestParam Date startDate) {
        return visitService.getAllByCityByIdClinicByCareTypeForWeek(city, idClinic, CareType, startDate);
    }

    @GetMapping("/visits/doctorAndSpecializationAndCityAndClinic")
    public List<Visit> getAllVisitsByIdDoctorBySpecializationByCityAndClinicForWeek(@RequestParam Long idDoctor,
                                                                                    @RequestParam String specialization,
                                                                                    @RequestParam String city,
                                                                                    @RequestParam Long idClinic,
                                                                                    @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorBySpecializationByCityByIdClinicForWeek(idDoctor, specialization, city, idClinic, startDate);
    }

    @GetMapping("/visits/doctorAndSpecializationAndCityAndCareType")
    public List<Visit> getAllVisitsByIdDoctorBySpecializationByCityAndCareTypeForWeek(@RequestParam Long idDoctor,
                                                                                      @RequestParam String specialization,
                                                                                      @RequestParam String city,
                                                                                      @RequestParam String careType,
                                                                                      @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorBySpecializationByCityByCareTypeForWeek(idDoctor, specialization, city, careType, startDate);
    }

    @GetMapping("/visits/doctorAndSpecializationAndClinicAndCareType")
    public List<Visit> getAllVisitsByIdDoctorBySpecializationByClinicAndCareTypeForWeek(@RequestParam Long idDoctor,
                                                                                        @RequestParam String specialization,
                                                                                        @RequestParam Long idClinic,
                                                                                        @RequestParam String CareType,
                                                                                        @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorBySpecializationByIdClinicByCareTypeForWeek(idDoctor, specialization, idClinic, CareType, startDate);
    }

    @GetMapping("/visits/doctorAndCityAndClinicAndCareType")
    public List<Visit> getAllVisitsByIdDoctorByCityByClinicByCareTypeForWeek(@RequestParam Long idDoctor,
                                                                             @RequestParam String city,
                                                                             @RequestParam Long idClinic,
                                                                             @RequestParam String careType,
                                                                             @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorByCityByIdClinicByCareTypeForWeek(idDoctor, city, idClinic, careType, startDate);
    }

    @GetMapping("/visits/specializationAndCityAndClinicAndCareType")
    public List<Visit> getAllVisitsBySpecializationByCityByClinicByCareTypeForWeek(@RequestParam String specialization,
                                                                                   @RequestParam String city,
                                                                                   @RequestParam Long idClinic,
                                                                                   @RequestParam String careType,
                                                                                   @RequestParam Date startDate) {
        return visitService.findAllBySpecializationByCityByIdClinicByCareTypeForWeek(specialization, city, idClinic, careType, startDate);
    }

    @GetMapping("/visits/doctorAndSpecializationAndCityAndClinicAndCareType")
    public List<Visit> getAllVisitsByIdDoctorBySpecializationByCityByClinicByCareTypeForWeek(@RequestParam Long idDoctor,
                                                                                             @RequestParam String specialization,
                                                                                             @RequestParam String city,
                                                                                             @RequestParam Long idClinic,
                                                                                             @RequestParam String careType,
                                                                                             @RequestParam Date startDate) {
        return visitService.findAllByIdDoctorBySpecializationByCityByIdClinicByCareTypeForWeek(idDoctor, specialization, city, idClinic, careType, startDate);
    }

}
