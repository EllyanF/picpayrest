package com.ellyanf.picpayrest.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionCompletedEvent extends ApplicationEvent {
    private final String email;
    private final String message;

    public TransactionCompletedEvent(Object source, String email, String message) {
        super(source);
        this.email = email;
        this.message = message;
    }

}
