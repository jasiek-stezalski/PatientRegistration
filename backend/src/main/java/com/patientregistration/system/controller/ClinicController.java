package com.patientregistration.system.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.Clinic;
import com.patientregistration.system.domain.View.Views;
import com.patientregistration.system.service.ClinicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping("/")
    @JsonView(Views.Basic.class)
    public List<Clinic> getAllClinics() {
        return clinicService.findAllClinics();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Basic.class)
    public Clinic getClinicById(@PathVariable(value = "id") Long idClinic) {
        return clinicService.findByClinicId(idClinic);
    }

    @PostMapping("/")
    @JsonView(Views.Basic.class)
    public Clinic createClinic(@Valid @RequestBody Clinic clinic) {
        return clinicService.saveOrUpdate(clinic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClinic(@PathVariable(value = "id") Long idClinic) {
        clinicService.delete(idClinic);

        return ResponseEntity.ok().build();
    }

}