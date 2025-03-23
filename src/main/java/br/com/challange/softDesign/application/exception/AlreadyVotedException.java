package br.com.challange.softDesign.application.exception;

public class AlreadyVotedException extends RuntimeException{

    public AlreadyVotedException(String message) {
        super(message);
    }
}
