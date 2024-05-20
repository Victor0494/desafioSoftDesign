package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.constant.Rating;

import java.math.BigDecimal;

public record CreateHotelDTO(
        String name,
        String localization,
        String checkIn,
        String checkOut,
        Integer numberOfRooms,
        Integer numberOfGuest,
        BigDecimal pricePerNight,
        ConvenienceDTO convenience,
        Rating rating) {
}
