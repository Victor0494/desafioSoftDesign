package br.com.desafio.ntconsult.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DATE_ALREADY_RESERVED("Date already reserved"),
    NUMBER_OF_ROOMS("Number of rooms is more than expected"),
    HOTEL_NOT_FOUND("Hotel not found"),
    INVALID_GUEST_INFORMATION("Invalid guest information");

    private final String message;
}