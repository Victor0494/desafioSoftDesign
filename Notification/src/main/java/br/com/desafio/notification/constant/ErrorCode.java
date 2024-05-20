package br.com.desafio.notification.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DATE_ALREADY_RESERVED("Date already reserved"),
    NUMBER_OF_ROOMS("Number of rooms is more than expected");

    private final String message;
}
