package com.ellyanf.picpayrest.exception;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(Long id) {
        super(String.format("User with ID %d does not exist.", id));
    }
}
