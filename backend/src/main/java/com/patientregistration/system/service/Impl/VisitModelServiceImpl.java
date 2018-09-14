package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.VisitModelRepository;
import com.patientregistration.system.service.VisitModelService;
import com.patientregistration.system.service.VisitService;
import org.joda.time.Days;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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
    public VisitModel save(VisitModel visitModel) {
        VisitModel newVisitModel = visitModelRepository.save(visitModel);

        LocalDate visitDate = newVisitModel.getStart().toLocalDate();
        LocalDate endDate = newVisitModel.getEndDate().toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        while (visitDate.isBefore(endDate) || visitDate.isEqual(endDate)) {
            // Chcek if it is a weekend
            if (newVisitModel.getDayInterval() == 1 && weekend.contains(visitDate.getDayOfWeek())) {
                visitDate = visitDate.plusDays(newVisitModel.getDayInterval());
                continue;
            }

            LocalTime visitHour = newVisitModel.getStart().toLocalTime();
            LocalTime endHour = newVisitModel.getEnd().toLocalTime();

            while (visitHour.isBefore(endHour)) {
                LocalDateTime term = LocalDateTime.parse(visitDate.toString() + " " + visitHour.toString(), formatter);
                visitService.save(new Visit(term, newVisitModel));
                visitHour = visitHour.plusMinutes(newVisitModel.getMinuteInterval());
            }

            visitDate = visitDate.plusDays(newVisitModel.getDayInterval());
        }

        return newVisitModel;
    }

    @Override
    public void delete(Long idVisitModel) {
        visitModelRepository.deleteById(idVisitModel);
    }

}
