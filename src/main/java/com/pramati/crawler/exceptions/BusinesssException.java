package com.pramati.crawler.exceptions;

public class BusinesssException extends Exception{

    private String message = null;

    public BusinesssException() {
        super();
    }

    public BusinesssException(String message) {
        super(message);
        this.message = message;
    }

    public BusinesssException(Throwable cause) {
        super(cause);
    }

    public BusinesssException(String message,Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
