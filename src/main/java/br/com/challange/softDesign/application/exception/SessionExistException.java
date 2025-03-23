package br.com.challange.softDesign.application.exception;

public class SessionExistException extends RuntimeException{

    public SessionExistException(String message) {
        super(message);
    }
}
