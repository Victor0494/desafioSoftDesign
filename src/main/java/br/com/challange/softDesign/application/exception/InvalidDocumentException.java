package br.com.challange.softDesign.application.exception;

public class InvalidDocumentException extends RuntimeException{

    public InvalidDocumentException(String message) {
        super(message);
    }
}
