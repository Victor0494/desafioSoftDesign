package br.com.challange.softDesign.domain.constant;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    INVALID_DOCUMENT("Invalid document"),
    UNABLE_TO_VOTE("User unable to vote"),
    ALREADY_VOTED("User already voted"),
    TOPIC_NOT_FOUND("Topic not found"),
    SESSION_FINISHED("Session already finished"),
    SESSION_NOT_FOUND("Session not found"),
    SESSION_ALREADY_EXIST("Session already exist to this topic"),
    TOPIC_ALREADY_CREATED("Topic already created"),
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXIST("User already exist");

    ErrorMessage(String message) {
        this.message = message;
    }

    private final String message;
}
