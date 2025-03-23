package br.com.challange.softDesign.application.exception;

public class TopicAlreadyCreatedException extends RuntimeException{

    public TopicAlreadyCreatedException(String message) {
        super(message);
    }
}
