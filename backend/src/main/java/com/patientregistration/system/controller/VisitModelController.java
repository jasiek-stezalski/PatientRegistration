package com.patientregistration.system.controller;

import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.service.VisitModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VisitModelController {

    private VisitModelService visitModelService;

    public VisitModelController(VisitModelService visitModelService) {
        this.visitModelService = visitModelService;
    }

    @GetMapping("/visitModels")
    public List<VisitModel> getAllVisitModels() {
        return visitModelService.findAllVisitModels();
    }

    @GetMapping("/visitModels/{id}")
    public VisitModel getVisitModelById(@PathVariable(value = "id") Long idVisitModel) {
        return visitModelService.findByIdVisitModel(idVisitModel);
    }

    @GetMapping("/visitModel/doctor/{id}")
    public List<VisitModel> getAllVisitModelsByDoctorId(@PathVariable(value = "id") Long idDoctor) {
        return visitModelService.findAllByIdDoctor(idDoctor);
    }

    @GetMapping("/visitModel/specialization")
    public List<VisitModel> getAllVisitModelsBySpecialization(@RequestParam String specialization) {
        return visitModelService.findAllBySpecialization(specialization);
    }

    @PostMapping("/visitModels")
    public VisitModel createVisitModel(@Valid @RequestBody VisitModel visitModel) {
        return visitModelService.saveOrUpdate(visitModel);
    }

    @DeleteMapping("/visitModels/{id}")
    public ResponseEntity<?> deleteVisitModel(@PathVariable(value = "id") Long idVisitModel) {
        visitModelService.delete(idVisitModel);

        return ResponseEntity.ok().build();
    }

}
