package com.patientregistration.system.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.patientregistration.system.domain.View.Views;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.service.VisitModelService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/visitModels")
public class VisitModelController {

    private VisitModelService visitModelService;

    public VisitModelController(VisitModelService visitModelService) {
        this.visitModelService = visitModelService;
    }

    @GetMapping("/doctor/{id}")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<VisitModel> getVisitModelsInWeek(@PathVariable(value = "id") Long idUser,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitModelService.findBetweenByDoctor(from, to, idUser);
    }

    @PostMapping("/")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public VisitModel createVisitModel(@RequestBody VisitModel data) {
        return visitModelService.save(data);
    }

    @PutMapping("/")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public VisitModel moveEvent(@RequestBody VisitModel data) {
        return visitModelService.move(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVisitModel(@PathVariable(value = "id") Long idVisitModel) {
        visitModelService.delete(idVisitModel);

        return ResponseEntity.ok().build();
    }

}
