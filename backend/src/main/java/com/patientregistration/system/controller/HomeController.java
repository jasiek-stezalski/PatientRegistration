//package com.patientregistration.system.controller;
//
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import com.patientregistration.system.domain.Event;
//import com.patientregistration.system.domain.Resource;
//import com.patientregistration.system.repository.EventRepository;
//import com.patientregistration.system.repository.ResourceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.*;
//
//import javax.transaction.Transactional;
//import java.time.LocalDateTime;
//
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
//@RestController
//public class HomeController {
//
//    @Autowired
//    EventRepository er;
//
//    @Autowired
//    ResourceRepository rr;
//
//    @GetMapping("/api")
//    @ResponseBody
//    public String home() {
//        return "Welcome!";
//    }
//
//    @GetMapping("/api/resources")
//    public Iterable<Resource> resources() {
//        return rr.findAll();
//    }
//
//    @GetMapping("/api/events")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    public Iterable<Event> events(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
//                           @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
//        return er.findBetween(from, to);
//    }
//
//    @PostMapping("/api/events/create")
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @Transactional
//    public Event createEvent(@RequestBody Event data) {
//        System.out.println(data.getText());
//        System.out.println(data.getEnd());
//        System.out.println(data.getStart());
//
//        Event e = new Event();
////        e.setStart(data.getStart());
////        e.setEnd(data.getEnd());
////        e.setText(data.getText());
////
////        er.save(e);
//
//        return e;
//    }
//
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
//
//}
