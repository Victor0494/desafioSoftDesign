package br.com.desafio.notification.listener;

import br.com.desafio.notification.dto.ReservationDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitListener {

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "reservation.confirmed")
    public void receivedConfirmedReservation(@Payload ReservationDetailsDTO reservationDetailsDTO) {
        log.info("RabbitListener.receivedConfirmedReservation: {}", reservationDetailsDTO);
        String message = """
                Id da reserva: %s
                Hotel: %s
                CheckIn: %s
                Responsável: %s
                """.formatted(reservationDetailsDTO.id(),
                reservationDetailsDTO.hotelName(),
                reservationDetailsDTO.checkIn(),
                reservationDetailsDTO.responsibleGuest());
        System.out.println(message);
        log.debug("RabbitListener.receivedConfirmedReservation: {}", message);
    }
}
