package com.ellyanf.picpayrest.event;

import com.ellyanf.picpayrest.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private final NotificationService notificationService;

    @Autowired
    public NotificationListener (NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleTransactionCompletedEvent(TransactionCompletedEvent event) {
        notificationService.sendNotification(event.getEmail(), event.getMessage());
    }
}
