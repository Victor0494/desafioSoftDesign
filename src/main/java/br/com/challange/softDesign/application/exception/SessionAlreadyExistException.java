package br.com.challange.softDesign.application.exception;

public class SessionAlreadyExistException extends RuntimeException{

    public SessionAlreadyExistException(String message) {
        super(message);
    }
}
