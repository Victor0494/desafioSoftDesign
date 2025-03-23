package br.com.challange.softDesign.application.exception;

public class SessionFinishedException extends RuntimeException{

    public SessionFinishedException(String message) {
        super(message);
    }
}
