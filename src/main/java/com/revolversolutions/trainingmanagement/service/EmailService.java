package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.MailBody;
import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.Map;

/**
 * @author Yassine CHALH
 */
public interface EmailService {


    @Async
    void sendEmail(String toEmail, String subject, String templatePath, Map<String, String> variables) throws MessagingException, IOException;

    @Async
    void sendSimpleEmail(MailBody mailBody);
}
