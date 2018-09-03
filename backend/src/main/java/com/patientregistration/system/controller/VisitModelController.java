package com.patientregistration.system.controller;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.service.ClinicService;
import com.patientregistration.system.service.UserService;
import com.patientregistration.system.service.VisitModelService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class VisitModelController {

    private VisitModelService visitModelService;
    private UserService userService;
    private ClinicService clinicService;

    public VisitModelController(VisitModelService visitModelService, UserService userService, ClinicService clinicService) {
        this.visitModelService = visitModelService;
        this.userService = userService;
        this.clinicService = clinicService;
    }

    @GetMapping("/visitModels")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public List<VisitModel> getAllVisitModelsInWeek(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitModelService.findBetween(from, to);
    }

    @GetMapping("/visitModels/{id}")
    public VisitModel getVisitModelById(@PathVariable(value = "id") Long idVisitModel) {
        return visitModelService.findByIdVisitModel(idVisitModel);
    }

//    @PostMapping("/visitModels")
//    public VisitModel createVisitModel(@Valid @RequestBody VisitModel visitModel) {
//        return visitModelService.save(visitModel);
//    }


    @PostMapping("/visitModels")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    public VisitModel createVisitModel(@RequestBody VisitModel data) {
//        VisitModel visitModel = visitModelService.findByIdVisitModel(10L);
//
//        VisitModel v = new VisitModel();
////
//        v.setStart(visitModel.getStart());
//        v.setEnd(visitModel.getEnd());
//
//        v.setEndDate(visitModel.getEndDate());
//        v.setMinuteInterval(visitModel.getMinuteInterval());
//        v.setDayInterval(visitModel.getDayInterval());
//        v.setSpecialization(visitModel.getSpecialization());
//        v.setCareType(visitModel.getCareType());
//        v.setUser(visitModel.getUser());
//        v.setClinic(visitModel.getClinic());
//
//        return visitModelService.save(v);
//
//        VisitModel visitModel = new VisitModel();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime joiningDate = LocalDateTime.parse("2017-03-03 09:00:00", formatter);
//        LocalDateTime joiningDate2 = LocalDateTime.parse("2017-03-03 11:00:00", formatter);
//
//        visitModel.setStart(joiningDate);
//        visitModel.setEnd(joiningDate2);
//
//        System.out.println(visitModel.getStart());
//        visitModel.setEndDate(Date.valueOf(data.getEndDate().toString()));
//        visitModel.setMinuteInterval(data.getDayInterval());
//        visitModel.setDayInterval(data.getDayInterval());
//        visitModel.setSpecialization(data.getSpecialization());
//        visitModel.setCareType(data.getCareType());
//        visitModel.setUser(userService.findUserById(data.getUser().getIdUser()));
//        visitModel.setClinic(clinicService.findByClinicId(data.getClinic().getIdClinic()));
//
////        data.setEnd(LocalDateTime.now());
////        data.setStart(LocalDateTime.now());
////
////
////        System.out.println(data.getSpecialization());
////        System.out.println(data.getCareType());
////        System.out.println(data.getDayInterval());
////        System.out.println(data.getEndDate());
////        System.out.println(data.getStart());
////        System.out.println(data.getEnd());
////
////
////        data.setUser(userService.findUserById(data.getUser().getIdUser()));
////        System.out.println(data.getUser().getIdUser());
////
////        data.setClinic(clinicService.findByClinicId(data.getClinic().getIdClinic()));
////        System.out.println(data.getClinic().getIdClinic());
//
//        return visitModelService.save(visitModel);
        return visitModelService.save(data);
    }

//    @PostMapping("/api/events/move")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @Transactional
//    public Event moveEvent(@RequestBody Event data) {
//
//        Event e = er.findById(data.getId()).get();
//        Resource r = rr.findById(data.getResourceId()).get();
//
//        e.setStart(data.getStart());
//        e.setEnd(data.getEnd());
//        e.setResource(r);
//
//        er.save(e);
//
//        return e;
//    }

    @DeleteMapping("/visitModels/{id}")
    public ResponseEntity<?> deleteVisitModel(@PathVariable(value = "id") Long idVisitModel) {
        visitModelService.delete(idVisitModel);

        return ResponseEntity.ok().build();
    }

}
