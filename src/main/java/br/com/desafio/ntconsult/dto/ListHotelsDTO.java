package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.constant.Rating;
import br.com.desafio.ntconsult.entities.Hotel;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
public record ListHotelsDTO(
                            Long id,
                            String name,
                            String localization,
                            List<String> checkIn,
                            List<String> checkOut,
                            Integer numberOfRooms,
                            Integer numberOfGuest,
                            BigDecimal pricePerNight,
                            ConvenienceDTO convenienceDTO,
                            Rating rating) {

    public ListHotelsDTO(Hotel hotel) {
        this(   hotel.getId(),
                hotel.getName(),
                hotel.getLocalization(),
                listOfValidDate(hotel.getCheckIn()),
                listOfValidDate(hotel.getCheckOut()),
                hotel.getNumberOfRooms(),
                hotel.getNumberOfGuest(),
                hotel.getPricePerNight(),
                new ConvenienceDTO(hotel.getConvenience()),
                hotel.getRating());
    }

    private static List<String> listOfValidDate(String checkIn) {
        return new ArrayList<>(Arrays.asList(checkIn.split(",")));

    }


}
