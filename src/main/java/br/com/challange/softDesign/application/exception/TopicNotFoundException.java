package br.com.challange.softDesign.application.exception;

public class TopicNotFoundException extends RuntimeException{

    public TopicNotFoundException(String message) {
        super(message);
    }
}
