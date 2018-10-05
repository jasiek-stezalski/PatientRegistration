package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitModelRepository;
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

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Calendar.DATE;

@Service
public class VisitModelServiceImpl implements VisitModelService {

    private VisitModelRepository visitModelRepository;
    private VisitService visitService;

    public VisitModelServiceImpl(VisitModelRepository visitModelRepository, VisitService visitService) {
        this.visitModelRepository = visitModelRepository;
        this.visitService = visitService;
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
        VisitModel newVisitModel = visitModelRepository.save(visitModel);

        LocalDate visitDate = newVisitModel.getStart().toLocalDate();
        LocalDate endDate = newVisitModel.getEndDate().toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        while (visitDate.isBefore(endDate) || visitDate.isEqual(endDate)) {
            // Check if it is a weekend
            if (newVisitModel.getDayInterval() == 1 && weekend.contains(visitDate.getDayOfWeek())) {
                visitDate = visitDate.plusDays(newVisitModel.getDayInterval());
                continue;
            }

            LocalTime visitHour = newVisitModel.getStart().toLocalTime();
            LocalTime endHour = newVisitModel.getEnd().toLocalTime();

            while (visitHour.isBefore(endHour)) {
                LocalDateTime startTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), formatter);
                visitHour = visitHour.plusMinutes(newVisitModel.getMinuteInterval());
                LocalDateTime endTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), formatter);
                if (endTerm.toLocalTime().isAfter(endHour)) {
                    newVisitModel.setEnd(startTerm);
                    VisitModel savedVisitModel = visitModelRepository.save(newVisitModel);
                    if (visitService.findAllByVisitModel(savedVisitModel).isEmpty()) {
                        visitService.save(new Visit(
                                startTerm,
                                endTerm,
                                startTerm.getHour() + " : " + (startTerm.getMinute() < 10 ? startTerm.getMinute() + "0" : startTerm.getMinute())
                                , newVisitModel));
                        newVisitModel.setEnd(endTerm);
                        visitModelRepository.save(newVisitModel);
                    }
                    break;
                } else {
                    visitService.save(new Visit(
                            startTerm,
                            endTerm,
                            startTerm.getHour() + " : " + (startTerm.getMinute() < 10 ? startTerm.getMinute() + "0" : startTerm.getMinute())
                            , newVisitModel));
                }

            }

            visitDate = visitDate.plusDays(newVisitModel.getDayInterval());
        }

        if (visitModelRepository.findById(newVisitModel.getId()).get().getVisits() == null) {
            visitModelRepository.deleteById(newVisitModel.getId());
            throw new ResourceNotFoundException("Empty list of visits");
        }
        return newVisitModel;
    }

    @Override
    public void delete(Long idVisitModel) {
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

        LocalDate visitDate = visitModel.getStart().toLocalDate();
        LocalDate endDate = visitModel.getEndDate().toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        int index = 0;
        while (visitDate.isBefore(endDate) || visitDate.isEqual(endDate)) {

            LocalTime visitHour = visitModel.getStart().toLocalTime();
            LocalTime endHour = visitModel.getEnd().toLocalTime();

            while (visitHour.isBefore(endHour)) {
                LocalDateTime startTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), formatter);
                visitHour = visitHour.plusMinutes(visitModel.getMinuteInterval());
                LocalDateTime endTerm = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), formatter);
                visits.get(index).setStart(startTerm);
                visits.get(index).setEnd(endTerm);
                if (visits.get(index).getUser() != null)
                    visits.get(index).setText("Zajęte");
                else
                    visits.get(index).setText(startTerm.getHour() + " : " + (startTerm.getMinute() < 10 ? startTerm.getMinute() + "0" : startTerm.getMinute()));
                index++;
            }
            visitDate = visitDate.plusDays(visitModel.getDayInterval());
        }

        visitModel.setVisits(visits);

        return visitModelRepository.save(visitModel);
    }

    private static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }

}
