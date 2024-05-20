package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.constant.Rating;
import br.com.desafio.ntconsult.entities.Hotel;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ListHotelsDTO(
                            Long id,
                            String name,
                            String localization,
                            String checkIn,
                            String checkOut,
                            Integer numberOfRooms,
                            Integer numberOfGuest,
                            BigDecimal pricePerNight,
                            ConvenienceDTO convenienceDTO,
                            Rating rating) {

    public ListHotelsDTO(Hotel hotel) {
        this(   hotel.getId(),
                hotel.getName(),
                hotel.getLocalization(),
                hotel.getCheckIn(),
                hotel.getCheckOut(),
                hotel.getNumberOfRooms(),
                hotel.getNumberOfGuest(),
                hotel.getPricePerNight(),
                new ConvenienceDTO(hotel.getConvenience()),
                hotel.getRating());
    }

}
