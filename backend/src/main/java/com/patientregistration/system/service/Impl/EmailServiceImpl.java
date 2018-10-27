package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.domain.Visit;
import com.patientregistration.system.service.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    public EmailServiceImpl(@Qualifier("getJavaMailSender") JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void cancelVisitsEmail(List<Visit> visits) {
        SimpleMailMessage message = new SimpleMailMessage();

        new Thread(() -> {

            message.setSubject("Rejestracja on-line. Wizyta została odwołana");

            for (Visit visit : visits) {
                if (visit.getUser() != null) {
                    message.setText("Z przykrością informujemy że wizyta z dnia "
                            + visit.getStart().toLocalDate()
                            + " z godziny "
                            + visit.getStart().toLocalTime()
                            + " została odwołana. Równocześnie zachęcamy do ponownej rejestracji.");
                    message.setTo(visit.getUser().getEmail());
                    emailSender.send(message);
                }
            }

        }).start();
    }

    @Override
    public void cancelVisitEmail(Visit visit) {
        SimpleMailMessage message = new SimpleMailMessage();

        new Thread(() -> {

            message.setSubject("Rejestracja on-line. Wizyta została odwołana");

            message.setText("Twoja wizyta z dnia "
                    + visit.getStart().toLocalDate()
                    + " z godziny "
                    + visit.getStart().toLocalTime()
                    + " została odwołana. Równocześnie zachęcamy do ponownej rejestracji.");
            message.setTo(visit.getUser().getEmail());
            emailSender.send(message);

        }).start();
    }

    @Override
    public void moveVisitEmail(List<Visit> visits, List<LocalDateTime> term) {
        SimpleMailMessage message = new SimpleMailMessage();

        new Thread(() -> {

            message.setSubject("Rejestracja on-line. Termin wizyty uległ zmianie");

            for (int i = 0; i < visits.size(); i++) {
                if (visits.get(i).getUser() != null) {
                    message.setText("Informujemy że wizyta z dnia "
                            + term.get(i).toLocalDate()
                            + " z godziny "
                            + term.get(i).toLocalTime()
                            + " została przesunięta na dzień "
                            + visits.get(i).getStart().toLocalDate()
                            + " na godzinę "
                            + visits.get(i).getStart().toLocalTime()
                            + ". Jeżeli nowy termin panu nie odpowiada zachęcamy do zmiany terminu.");
                    message.setTo(visits.get(i).getUser().getEmail());
                    emailSender.send(message);
                }
            }

        }).start();
    }

    @Override
    public void bookVisitEmail(Visit visit) {
        SimpleMailMessage message = new SimpleMailMessage();

        new Thread(() -> {

            message.setSubject("Rejestracja on-line");
            message.setText("Dziękujemy. Twoja rezerwacja została potwierdzona przez placówkę medyczną. \n" +
                    "Zapraszamy na wizytę. \n\nTermin wizyty to: "
                    + visit.getStart().toLocalDate() + " "
                    + visit.getStart().toLocalTime()
                    + ". \nLekarz: "
                    + visit.getVisitModel().getUser().getFirstName() + " "
                    + visit.getVisitModel().getUser().getLastName()
                    + "\nTyp wizyty: "
                    + visit.getVisitModel().getCareType()
                    + "\nPlacówka medyczna: "
                    + visit.getVisitModel().getClinic().getName()
                    + "\nAdress: "
                    + visit.getVisitModel().getClinic().getAddress() + ", "
                    + visit.getVisitModel().getClinic().getCity()
                    + "\nTelefon: "
                    + visit.getVisitModel().getClinic().getPhoneNumber());
            message.setTo(visit.getUser().getEmail());
            emailSender.send(message);

        }).start();
    }

    @Override
    public void freeSlotVisitEmail(Visit freeVisit, List<Visit> allVisits, User user) {
        SimpleMailMessage message = new SimpleMailMessage();

        new Thread(() -> {

            for (Visit visit : allVisits) {
                if (!visit.getUser().equals(user)) {
                    message.setSubject("Rejestracja on-line. Dostępny wcześniejszy termin wizyty");
                    message.setText("Pragniemy poinformować, że zwolnił się termin wizyty przed pańską wizytą.\n" +
                            "\n\nTermin wizyty to: "
                            + visit.getStart().toLocalDate() + " "
                            + visit.getStart().toLocalTime()
                            + ". \nLekarz: "
                            + visit.getVisitModel().getUser().getFirstName() + " "
                            + visit.getVisitModel().getUser().getLastName()
                            + "\nTyp wizyty: "
                            + visit.getVisitModel().getCareType()
                            + "\nPlacówka medyczna: "
                            + visit.getVisitModel().getClinic().getName()
                            + "\nAdress: "
                            + visit.getVisitModel().getClinic().getAddress() + ", "
                            + visit.getVisitModel().getClinic().getCity()
                            + "\nTelefon: "
                            + visit.getVisitModel().getClinic().getPhoneNumber() +
                            "\n\nW celu zmiany terminu należy wejść na naszą stronę, " +
                            "a następnie przejść do zakładki Panel użytkownika -> Aktualne wizyty.");
                    message.setTo(visit.getUser().getEmail());
                    emailSender.send(message);
                }
            }

        }).start();
    }

    @Override
    public void changeVisitEmail(Visit visit) {
        SimpleMailMessage message = new SimpleMailMessage();

        new Thread(() -> {

            message.setSubject("Rejestracja on-line. Zmiana terminu wizyty");
            message.setText("Dziękujemy. Termin twojej wizyty został zmieniony. \n" +
                    "Zapraszamy na wizytę. \n\nTermin wizyty to: "
                    + visit.getStart().toLocalDate() + " "
                    + visit.getStart().toLocalTime()
                    + ". \nLekarz: "
                    + visit.getVisitModel().getUser().getFirstName() + " "
                    + visit.getVisitModel().getUser().getLastName()
                    + "\nTyp wizyty: "
                    + visit.getVisitModel().getCareType()
                    + "\nPlacówka medyczna: "
                    + visit.getVisitModel().getClinic().getName()
                    + "\nAdress: "
                    + visit.getVisitModel().getClinic().getAddress() + ", "
                    + visit.getVisitModel().getClinic().getCity()
                    + "\nTelefon: "
                    + visit.getVisitModel().getClinic().getPhoneNumber());
            message.setTo(visit.getUser().getEmail());
            emailSender.send(message);

        }).start();
    }
}
