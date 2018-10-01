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
public class VisitModelController {

    private VisitModelService visitModelService;

    public VisitModelController(VisitModelService visitModelService) {
        this.visitModelService = visitModelService;
    }

    @GetMapping("/visitModels")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public List<VisitModel> getAllVisitModelsInWeek(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitModelService.findBetween(from, to);
    }

    @GetMapping("/visitModels/{id}")
    @JsonView(Views.Basic.class)
    public VisitModel getVisitModelById(@PathVariable(value = "id") Long idVisitModel) {
        System.out.println("to się wywołuje");
        VisitModel byIdVisitModel = visitModelService.findByIdVisitModel(idVisitModel);
        System.out.println(byIdVisitModel.getClinic());
        return byIdVisitModel;
    }

    @PostMapping("/visitModels")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public VisitModel createVisitModel(@RequestBody VisitModel data) {
        return visitModelService.save(data);
    }

    @PutMapping("/visitModels")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.Basic.class)
    public VisitModel moveEvent(@RequestBody VisitModel data) {
        return visitModelService.move(data);
    }

    @DeleteMapping("/visitModels/{id}")
    public ResponseEntity<?> deleteVisitModel(@PathVariable(value = "id") Long idVisitModel) {
        visitModelService.delete(idVisitModel);

        return ResponseEntity.ok().build();
    }

}
