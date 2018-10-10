package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitModelRepository;
import com.patientregistration.system.service.EmailService;
import com.patientregistration.system.service.VisitModelService;
import com.patientregistration.system.service.VisitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Calendar.DATE;

@Service
public class VisitModelServiceImpl implements VisitModelService {

    private VisitModelRepository visitModelRepository;
    private VisitService visitService;
    private EmailService emailService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final Set<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public VisitModelServiceImpl(VisitModelRepository visitModelRepository, VisitService visitService, EmailService emailService) {
        this.visitModelRepository = visitModelRepository;
        this.visitService = visitService;
        this.emailService = emailService;
    }

    @Override
    public List<VisitModel> findBetween(LocalDateTime from, LocalDateTime to) {
        return visitModelRepository.findBetween(from, to);
    }

    @Override
    public VisitModel findByIdVisitModel(Long idVisitModel) {
        return visitModelRepository.findById(idVisitModel)
                .orElseThrow(() -> new ResourceNotFoundException(idVisitModel.toString()));
    }

    @Override
    @Transactional
    public VisitModel save(VisitModel visitModel) {

        // Problem when we want to create visit that is not perfectly fit
        // visits are good created but model not
        // more than one problem xD

        List<Visit> visits = new ArrayList<>();

        LocalDate visitDate = visitModel.getStart().toLocalDate();

        while (!visitDate.isAfter(visitModel.getEndDate().toLocalDate())) {

            // Check if it is a weekend
            System.out.println(visitDate.getDayOfWeek());
            if (visitModel.getDayInterval() == 1 && WEEKEND.contains(visitDate.getDayOfWeek())) {
                System.out.println("jetem tu");
                visitDate = visitDate.plusDays(visitModel.getDayInterval());
                continue;
            }

            LocalTime visitHour = visitModel.getStart().toLocalTime();
            LocalTime endHour = visitModel.getEnd().toLocalTime();

            while (visitHour.isBefore(endHour)) {

                LocalDateTime startTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), FORMATTER);
                visitHour = visitHour.plusMinutes(visitModel.getMinuteInterval());
                LocalDateTime endTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), FORMATTER);

                visits.add(new Visit(
                        startTerm,
                        endTerm,
                        startTerm.getHour() + " : " + (startTerm.getMinute() < 10 ? startTerm.getMinute() + "0" : startTerm.getMinute())
                        , visitModel));

                if (endTerm.toLocalTime().isAfter(endHour)) visitModel.setEnd(startTerm);

            }

            visitDate = visitDate.plusDays(visitModel.getDayInterval());

        }

        if (visits.isEmpty()) throw new ResourceNotFoundException("No matching visits to visit model");

        VisitModel savedVisitModel = visitModelRepository.save(visitModel);
        visitService.saveAll(visits);
        return savedVisitModel;
    }

//
//        VisitModel newVisitModel = visitModelRepository.save(visitModel);
//
//        LocalDate visitDate = newVisitModel.getStart().toLocalDate();
//
//        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
//
//        int visitCounter = 0;
//
//        while (!visitDate.isAfter(newVisitModel.getEndDate().toLocalDate())) {
//            // Check if it is a weekend
//            if (newVisitModel.getDayInterval() == 1 && weekend.contains(visitDate.getDayOfWeek())) {
//                visitDate = visitDate.plusDays(newVisitModel.getDayInterval());
//                continue;
//            }
//
//            LocalTime visitHour = newVisitModel.getStart().toLocalTime();
//            LocalTime endHour = newVisitModel.getEnd().toLocalTime();
//
//            while (visitHour.isBefore(endHour)) {
//                LocalDateTime startTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), FORMATTER);
//                visitHour = visitHour.plusMinutes(newVisitModel.getMinuteInterval());
//                LocalDateTime endTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), FORMATTER);
//                if (endTerm.toLocalTime().isAfter(endHour)) {
//                    newVisitModel.setEnd(startTerm);
//                    VisitModel savedVisitModel = visitModelRepository.save(newVisitModel);
//                    if (visitService.findAllByVisitModel(savedVisitModel).isEmpty()) {
//                        visitService.save(new Visit(
//                                startTerm,
//                                endTerm,
//                                startTerm.getHour() + " : " + (startTerm.getMinute() < 10 ? startTerm.getMinute() + "0" : startTerm.getMinute())
//                                , newVisitModel));
//                        visitCounter++;
//                        newVisitModel.setEnd(endTerm);
//                        visitModelRepository.save(newVisitModel);
//
//                    }
//                    break;
//                } else {
//                    visitService.save(new Visit(
//                            startTerm,
//                            endTerm,
//                            startTerm.getHour() + " : " + (startTerm.getMinute() < 10 ? startTerm.getMinute() + "0" : startTerm.getMinute())
//                            , newVisitModel));
//                    visitCounter++;
//                }
//
//            }
//
//            visitDate = visitDate.plusDays(newVisitModel.getDayInterval());
//        }
//
//        if (visitCounter == 0) {
//            visitModelRepository.deleteById(newVisitModel.getId());
//            throw new ResourceNotFoundException("No matching visits to visit model");
//        }
//        return newVisitModel;
//    }

    @Override
    @Transactional
    public void delete(Long idVisitModel) {
        emailService.cancelVisitEmail(findByIdVisitModel(idVisitModel).getVisits());
        visitModelRepository.deleteById(idVisitModel);
    }

    @Override
    @Transactional
    public VisitModel move(VisitModel data) {

        VisitModel visitModel = findByIdVisitModel(data.getId());

        int daysBetween = (int) DAYS.between(visitModel.getStart().toLocalDate(), data.getStart().toLocalDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(visitModel.getEndDate());
        calendar.add(DATE, daysBetween);
        visitModel.setEndDate(convertFromJAVADateToSQLDate(calendar.getTime()));

        visitModel.setEnd(data.getEnd());
        visitModel.setStart(data.getStart());

        List<Visit> visits = visitModel.getVisits();
        visits.sort(Comparator.comparing(Visit::getStart));

        List<LocalDateTime> term = visits.stream().map(Visit::getStart).collect(Collectors.toList());

        LocalDate visitDate = visitModel.getStart().toLocalDate();

        int index = 0;

        while (!visitDate.isAfter(visitModel.getEndDate().toLocalDate())) {

            LocalTime visitHour = visitModel.getStart().toLocalTime();
            LocalTime endHour = visitModel.getEnd().toLocalTime();

            while (visitHour.isBefore(endHour)) {

                LocalDateTime startTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), FORMATTER);
                visitHour = visitHour.plusMinutes(visitModel.getMinuteInterval());
                LocalDateTime endTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), FORMATTER);

                if (!visitDate.isBefore(LocalDate.now().plusDays(1L))) {
                    visits.get(index).setStart(startTerm);
                    visits.get(index).setEnd(endTerm);
                    if (visits.get(index).getUser() != null)
                        visits.get(index).setText("Zajęte");
                    else
                        visits.get(index).setText(startTerm.getHour() + " : " + (startTerm.getMinute() < 10 ? startTerm.getMinute() + "0" : startTerm.getMinute()));
                }

                index++;
            }
            visitDate = visitDate.plusDays(visitModel.getDayInterval());
        }


        visitModel.setVisits(visits);

        VisitModel savedVisitModel = visitModelRepository.save(visitModel);
        emailService.moveVisitEmail(savedVisitModel.getVisits(), term);
        return savedVisitModel;
    }

    private static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }

}
