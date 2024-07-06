package com.ellyanf.picpayrest.service;

import com.ellyanf.picpayrest.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationService {
    private final RestTemplate restTemplate;

    @Value("${app.authorizationUrl}")
    private String authUrl;

    @Autowired
    public AuthorizationService (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /* At the current time, the mocked url which is used to simulate the authorization randomly returns 200 or 403 */
    public boolean isTransactionAuthorized(TransactionDTO transactionDTO) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(authUrl, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException exception) {
            return false;
        }
    }
}
