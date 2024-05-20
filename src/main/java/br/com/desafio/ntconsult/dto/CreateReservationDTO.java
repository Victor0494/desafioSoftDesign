package br.com.desafio.ntconsult.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateReservationDTO(@NotNull Long hotelId, @NotNull Integer numberOfRooms, @NotNull Integer numberOfGuest, @NotBlank String checkIn, @NotBlank String checkOut, GuestDTO guest) {
}
