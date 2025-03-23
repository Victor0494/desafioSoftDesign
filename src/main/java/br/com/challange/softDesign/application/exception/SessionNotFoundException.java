package br.com.challange.softDesign.application.exception;

public class SessionNotFoundException extends RuntimeException{

    public SessionNotFoundException(String message) {
        super(message);
    }
}
