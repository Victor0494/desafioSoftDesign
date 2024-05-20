package br.com.desafio.ntconsult.exception;

public class DateAlreadyReservedException extends RuntimeException {

    public DateAlreadyReservedException(String message) {
        super(message);
    }
}
