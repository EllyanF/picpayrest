package com.ellyanf.picpayrest.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds. Transaction cannot be carried out.");
    }
}
