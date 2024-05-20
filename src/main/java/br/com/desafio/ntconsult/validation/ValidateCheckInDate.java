package br.com.desafio.ntconsult.validation;

import br.com.desafio.ntconsult.constant.ErrorCode;
import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.entities.Hotel;
import br.com.desafio.ntconsult.exception.DateAlreadyReservedException;
import br.com.desafio.ntconsult.exception.HotelNotFoundException;
import br.com.desafio.ntconsult.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class ValidateCheckInDate implements ReservationValidation {

    private final HotelRepository hotelRepository;

    @Override
    public void validateReservation(CreateReservationDTO reservedDTO) {
        log.debug("ValidateCheckInDate.validateReservation - Start - Input: reservedDTO: {}", reservedDTO);
        Optional<Hotel> hotel = hotelRepository.findById(reservedDTO.hotelId());
        if(hotel.isEmpty()) {
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND.getMessage());
        }
        Hotel hotelContent = hotel.get();

        List<String> list = new ArrayList<>(Arrays.asList(hotelContent.getCheckIn().split(",")));

        list.stream()
                .filter(StringTime -> (StringTime.contains(reservedDTO.checkIn()) && hotelContent.getNumberOfRooms() >= reservedDTO.numberOfRooms()))
                .findAny().orElseThrow(() -> new DateAlreadyReservedException(ErrorCode.DATE_ALREADY_RESERVED.getMessage()));
    }
}
