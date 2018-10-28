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

    @GetMapping("/idDoctor/{idDoctor}")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getVisitsByDoctor(@PathVariable(value = "idDoctor") Long idDoctor) {
        return visitService.findAllByDoctor(idDoctor);
    }

    @GetMapping("/doctor/{id}")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getVisitsInWeekByDoctor(@PathVariable(value = "id") Long idUser,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitService.findBetweenByDoctor(from, to, idUser);
    }

    @GetMapping("/filterLimited/")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getVisitsByVisitFilterLimited(@RequestParam String careType,
                                                     @RequestParam String city,
                                                     @RequestParam Long idClinic,
                                                     @RequestParam String specialization) {
        return visitService.findAllByFilterLimit5(careType, city, idClinic, specialization);
    }

    @GetMapping("/filter/")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getVisitsByVisitFilter(@RequestParam String careType,
                                              @RequestParam String city,
                                              @RequestParam Long idClinic,
                                              @RequestParam String specialization) {
        return visitService.findAllByFilter(careType, city, idClinic, specialization);
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

    @GetMapping("/user/day/")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<Visit> getVisitsByIdUserAndDay(@RequestParam Long idUser,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime day) {
        return visitService.findAllByIdUserAndDay(idUser, day);
    }

    @PutMapping("/book/")
    @JsonView(Views.Basic.class)
    public Visit bookVisit(@RequestBody Visit visit, @RequestParam Long idUser) {
        return visitService.bookVisit(visit, idUser);
    }

    @PutMapping("/bookByDoctor/")
    @JsonView(Views.Basic.class)
    public Visit bookVisitByDoctor(@RequestBody Visit visit, @RequestParam Long idUser) {
        return visitService.bookVisitByDoctor(visit, idUser);
    }

    @PutMapping("/change/")
    @JsonView(Views.Basic.class)
    public Visit changeVisit(@RequestBody Visit newVisit, @RequestParam Long idOldVisit) {
        return visitService.changeVisit(newVisit, idOldVisit);
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

    @PutMapping("/cancel/")
    @JsonView(Views.Basic.class)
    public Visit cancelVisit(@RequestBody Visit visit) {
        return visitService.cancel(visit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVisit(@PathVariable(value = "id") Long idVisit) {
        visitService.delete(idVisit);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/dataUpdate/")
    public void dataUpdate() {
        visitService.dataUpdate();
    }

}
