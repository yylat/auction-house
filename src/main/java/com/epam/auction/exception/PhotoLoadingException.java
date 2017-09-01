package com.epam.auction.exception;

public class PhotoLoadingException extends Exception {

    public PhotoLoadingException() {
    }

    public PhotoLoadingException(String message) {
        super(message);
    }

    public PhotoLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoLoadingException(Throwable cause) {
        super(cause);
    }

}