package com.patientregistration.system.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.patientregistration.system.domain.View.Views;
import com.patientregistration.system.domain.View.VisitFilter;
import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.service.VisitService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/visits")
public class VisitController {

    private VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getVisitsInWeek(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitService.findBetween(from, to);
    }

    @GetMapping("/doctor/{id}")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getVisitsInWeekByDoctor(@PathVariable(value = "id") Long idUser,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitService.findBetweenByDoctor(from, to, idUser);
    }

    @PostMapping("/filter/")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getVisitsByVisitFilter(@RequestBody VisitFilter visitFilter) {
        System.out.println(visitFilter.toString());
        return visitService.findAllByVisitFilter(visitFilter);
    }

    @GetMapping("/historical/user/{idUser}")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getHistoricalVisitsByIdUser(@PathVariable Long idUser) {
        return visitService.findAllHistoricalByIdUser(idUser);
    }

    @GetMapping("/actual/user/{idUser}")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getActualVisitsByIdUser(@PathVariable Long idUser) {
        return visitService.findAllActualByIdUser(idUser);
    }

    @PutMapping("/book/")
    @JsonView(Views.Basic.class)
    public Visit bookVisit(@RequestBody Visit visit, @RequestParam Long idUser) {
        return visitService.bookVisit(visit, idUser);
    }

    @PutMapping("/confirm/")
    @JsonView(Views.Basic.class)
    public Visit confirmVisit(@RequestBody Visit visit) {
        return visitService.confirmVisit(visit);
    }

    @PutMapping("/")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public Visit moveEvent(@RequestBody Visit visit) {
        return visitService.move(visit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVisit(@PathVariable(value = "id") Long idVisit) {
        visitService.delete(idVisit);

        return ResponseEntity.ok().build();
    }

}
