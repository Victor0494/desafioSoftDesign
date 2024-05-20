package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.entities.Convenience;
import br.com.desafio.ntconsult.entities.Hotel;

import java.math.BigDecimal;

public record CreateHotelDetailsDTO(Long id,
                                    String name,
                                    String localization,
                                    String checkIn,
                                    String checkOut,
                                    Integer numberOfRooms,
                                    Integer numberOfGuest,
                                    BigDecimal pricePerNight,
                                    Convenience convenience) {

    public CreateHotelDetailsDTO(Hotel hotel) {
        this(hotel.getId(),
                hotel.getName(),
                hotel.getLocalization(),
                hotel.getCheckIn(),
                hotel.getCheckOut(),
                hotel.getNumberOfRooms(),
                hotel.getNumberOfGuest(),
                hotel.getPricePerNight(),
                hotel.getConvenience());
    }
}
