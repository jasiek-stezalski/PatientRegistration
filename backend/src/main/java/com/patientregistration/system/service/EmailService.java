package com.patientregistration.system.service;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text) throws MessagingException, IOException;

}
