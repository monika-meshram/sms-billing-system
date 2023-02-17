package com.sms.billing.Exceptions;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}