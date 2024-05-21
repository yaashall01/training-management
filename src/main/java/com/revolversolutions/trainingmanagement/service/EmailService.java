package com.revolversolutions.trainingmanagement.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

/**
 * @author Yassine CHALH
 */
public interface EmailService {
    @Async
    void sendEmail(SimpleMailMessage email);
}
