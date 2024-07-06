package com.ellyanf.picpayrest.service;

import com.ellyanf.picpayrest.dto.NotificationBodyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;

    @Value("${app.notificationUrl}")
    private String notificationUrl;

    @Autowired
    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(String email, String message) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(notificationUrl, new NotificationBodyDTO(email, message), String.class);
            if (response.getStatusCode().is2xxSuccessful()) System.out.print("Notification Sent Successfully.");
        } catch (HttpClientErrorException exception) {
            System.out.print("Could not send notification.");
        }
    }
}
