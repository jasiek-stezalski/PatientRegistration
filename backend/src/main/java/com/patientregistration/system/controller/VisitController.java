package com.patientregistration.system.controller;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.service.VisitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return visitService.saveOrUpdate(visit);
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<?> deleteVisit(@PathVariable(value = "id") Long idVisit) {
        visitService.delete(idVisit);

        return ResponseEntity.ok().build();
    }

}
