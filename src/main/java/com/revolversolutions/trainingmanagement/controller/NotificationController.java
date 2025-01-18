package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.entity.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public Notification sendNotification(@Payload Notification notification) {
        return notification;
    }
}
