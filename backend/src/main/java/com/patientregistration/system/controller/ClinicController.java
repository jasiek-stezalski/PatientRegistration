package com.patientregistration.system.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.Clinic;
import com.patientregistration.system.domain.View.Views;
import com.patientregistration.system.service.ClinicService;
import org.springframework.web.bind.annotation.*;

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
    public List<Clinic> getClinics() {
        return clinicService.findAllClinics();
    }

    @GetMapping("/user")
    public List<Clinic> getClinicsByUser(@RequestParam String idUser) {
        return clinicService.findAllClinicsByUser(Long.valueOf(idUser));
    }

}