package com.patientregistration.system.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.patientregistration.system.domain.View.Views;
import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.service.VisitService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class VisitController {

    private VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/visits")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getAllVisitsInWeek(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitService.findBetween(from, to);
    }

    @GetMapping("/visits/{id}")
    public Visit getVisitById(@PathVariable(value = "id") Long idVisit) {
        return visitService.findByVisitId(idVisit);
    }

    @GetMapping("/visits/book/{id}")
    @JsonView(Views.Basic.class)
    public Visit bookVisit(@PathVariable(value = "id") Long idVisit, @RequestParam Long idUser) {
        return visitService.bookVisit(idVisit, idUser);
    }

    @GetMapping("visits/user/{id}")
    public List<Visit> getVisitByIdUser(@PathVariable(value = "id") Long idUser) {
        return visitService.findAllVisitsByIdUser(idUser);
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

}
