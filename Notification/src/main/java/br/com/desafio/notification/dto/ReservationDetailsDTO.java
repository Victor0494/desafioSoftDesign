package br.com.desafio.notification.dto;


import lombok.Getter;

import java.time.LocalDateTime;

public record ReservationDetailsDTO(Long id, String hotelName, Integer numberOfRooms, Integer numberOfGuest, String checkIn, GuestDTO responsibleGuest) {

}
