package com.epam.auction.exception;

public class ReceiverLayerException extends Exception {
    public ReceiverLayerException() {
    }

    public ReceiverLayerException(String message) {
        super(message);
    }

    public ReceiverLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiverLayerException(Throwable cause) {
        super(cause);
    }
}