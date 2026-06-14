package com.deepak.order_service.exception;

public class QunatityUnavailable extends RuntimeException {
    public QunatityUnavailable(String message) {
        super(message);
    }
}
