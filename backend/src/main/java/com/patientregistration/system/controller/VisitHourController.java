package com.patientregistration.system.controller;

import com.patientregistration.system.domain.VisitHour;
import com.patientregistration.system.service.VisitHourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VisitHourController {

    private VisitHourService visitHourService;

    public VisitHourController(VisitHourService visitHourService) {
        this.visitHourService = visitHourService;
    }

    @GetMapping("/visitHours")
    public List<VisitHour> getAllVisitHours() {
        return visitHourService.findAllVisitHours();
    }

    @GetMapping("/visitHours/{id}")
    public VisitHour getVisitHourById(@PathVariable(value = "id") Long idVisitHour) {
        return visitHourService.findByVisitHourId(idVisitHour);
    }

    @PostMapping("/visitHours")
    public VisitHour createVisitHour(@Valid @RequestBody VisitHour visitHour) {
        return visitHourService.saveOrUpdate(visitHour);
    }

    @DeleteMapping("/visitHours/{id}")
    public ResponseEntity<?> deleteVisitHour(@PathVariable(value = "id") Long idVisitHour) {
        visitHourService.delete(idVisitHour);

        return ResponseEntity.ok().build();
    }

}
