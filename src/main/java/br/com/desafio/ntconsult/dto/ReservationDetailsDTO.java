package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.entities.Reservation;
import lombok.Builder;

@Builder
public record ReservationDetailsDTO(Long id, String hotelName, Integer numberOfRooms, Integer numberOfGuest, String checkIn, GuestDTO responsibleGuest) {

    public ReservationDetailsDTO(Reservation reservation) {
        this(reservation.getId(), reservation.getHotelName(), reservation.getNumberOfRooms(), reservation.getNumberOfGuest(), reservation.getCheckIn(), new GuestDTO(reservation.getResponsibleGuest()));
    }
}
