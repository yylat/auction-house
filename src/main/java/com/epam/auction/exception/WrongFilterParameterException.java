package com.epam.auction.exception;

public class WrongFilterParameterException extends Exception {

    public WrongFilterParameterException() {
    }

    public WrongFilterParameterException(String message) {
        super(message);
    }

    public WrongFilterParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongFilterParameterException(Throwable cause) {
        super(cause);
    }
}