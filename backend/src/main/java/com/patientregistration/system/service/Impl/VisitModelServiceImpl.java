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
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Calendar.DATE;

@Service
public class VisitModelServiceImpl implements VisitModelService {

    private VisitModelRepository visitModelRepository;
    private VisitService visitService;
    private EmailService emailService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Set<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    public VisitModelServiceImpl(VisitModelRepository visitModelRepository, VisitService visitService, EmailService emailService) {
        this.visitModelRepository = visitModelRepository;
        this.visitService = visitService;
        this.emailService = emailService;
    }

    @Override
    public List<VisitModel> findBetweenByDoctor(LocalDateTime from, LocalDateTime to, Long idUser) {
        List<VisitModel> visitModels = visitModelRepository.findBetween(from, to);
        return visitModels
                .stream()
                .filter(v -> v.getUser().getId().equals(idUser))
                .collect(Collectors.toList());
    }

    @Override
    public VisitModel findByIdVisitModel(Long idVisitModel) {
        return visitModelRepository.findById(idVisitModel)
                .orElseThrow(() -> new ResourceNotFoundException(idVisitModel.toString()));
    }

    @Override
    @Transactional
    public VisitModel save(VisitModel visitModel) {

        List<Visit> visits = new ArrayList<>();

        LocalDate visitDate = visitModel.getStart().toLocalDate();

        while (!visitDate.isAfter(visitModel.getEndDate().toLocalDate())) {

            // Check if it is a weekend
            if (visitModel.getDayInterval() == 1 && WEEKEND.contains(visitDate.getDayOfWeek())) {
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

                if (endTerm.toLocalTime().isAfter(endHour)) {
                    visitModel.setEnd(LocalDateTime.parse(visitModel.getStart().toLocalDate().toString() + " " + visitHour.toString(), FORMATTER));
                    break;
                }

            }

            visitDate = visitDate.plusDays(visitModel.getDayInterval());

        }

        if (visits.isEmpty()) throw new ResourceNotFoundException("No matching visits to visit model");

        VisitModel savedVisitModel = visitModelRepository.save(visitModel);
        visitService.saveAll(visits);
        return savedVisitModel;
    }

    @Override
    @Transactional
    public VisitModel move(VisitModel data) {

        VisitModel visitModel = findByIdVisitModel(data.getId());

        List<LocalDateTime> term = visitModel.getVisits().stream().map(Visit::getStart).collect(Collectors.toList());

        updateDate(data, visitModel);

        visitModel.setEndDate(updateEndDate(data, visitModel));
        visitModel.setEnd(data.getEnd());
        visitModel.setStart(data.getStart());

        VisitModel savedVisitModel = visitModelRepository.save(visitModel);
        emailService.moveVisitEmail(savedVisitModel.getVisits(), term);
        return savedVisitModel;
    }

    @Override
    @Transactional
    public void delete(Long idVisitModel) {
        emailService.cancelVisitEmail(findByIdVisitModel(idVisitModel).getVisits());
        visitModelRepository.deleteById(idVisitModel);
    }

    /**
     * Method move endDate adding days between old start date and new start date
     *
     * @return updated endDate
     */
    private Date updateEndDate(VisitModel newVisitModel, VisitModel oldVisitModel) {
        int daysBetween = (int) DAYS.between(oldVisitModel.getStart().toLocalDate(), newVisitModel.getStart().toLocalDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldVisitModel.getEndDate());
        calendar.add(DATE, daysBetween);
        return convertFromJAVADateToSQLDate(calendar.getTime());
    }

    private void updateDate(VisitModel oldVisitModel, VisitModel newVisitModel) {

        long daysBetween = DAYS.between(newVisitModel.getStart().toLocalDate(), oldVisitModel.getStart().toLocalDate());
        long minutesBetween = MINUTES.between(newVisitModel.getStart().toLocalTime(), oldVisitModel.getStart().toLocalTime());


        for (Visit visit : newVisitModel.getVisits()) {

            if (visit.getStart().toLocalDate().isAfter(LocalDate.now())) {
                visit.setStart(visit.getStart().plusDays(daysBetween).plusMinutes(minutesBetween));
                visit.setEnd(visit.getEnd().plusDays(daysBetween).plusMinutes(minutesBetween));

                // Check if it is a weekend
                if (newVisitModel.getDayInterval() == 1 && WEEKEND.contains(visit.getStart().getDayOfWeek())) {
                    if (daysBetween < 0) {
                        visit.setStart(visit.getStart().plusDays(-2));
                        visit.setEnd(visit.getEnd().plusDays(-2));
                    } else {
                        visit.setStart(visit.getStart().plusDays(2));
                        visit.setEnd(visit.getEnd().plusDays(2));
                    }

                }
            }
            visit.setText(visit.getUser() == null
                    ? visit.getStart().getHour() + " : " + (visit.getStart().getMinute() < 10 ? visit.getStart().getMinute() + "0" : visit.getStart().getMinute())
                    : "Zajęte");
        }
    }

    /**
     * Method convert java.util.Date to java.sql.Date
     */
    private static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }

}
