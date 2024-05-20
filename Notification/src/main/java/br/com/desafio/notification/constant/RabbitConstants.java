package br.com.desafio.notification.constant;

public class RabbitConstants {

    public static final String RESERVATION_EXCHANGE = "reservation.ex";
    public static final String RESERVATION_QUEUE = "reservation.confirmed";
    public static final String RESERVATION_EXCHANGE_DLQ = "reservation.dlx";
    public static final String RESERVATION_QUEUE_DLQ = "reservation.confirmed-dlq";
}
