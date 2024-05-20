package br.com.desafio.ntconsult.validation;

import br.com.desafio.ntconsult.constant.ErrorCode;
import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.entities.Hotel;
import br.com.desafio.ntconsult.exception.NumberOfRoomsException;
import br.com.desafio.ntconsult.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ValidateNumberOfRooms implements ReservationValidation{

    private final HotelRepository hotelRepository;

    @Override
    public void validateReservation(CreateReservationDTO reservedDTO) {
        log.debug("ValidateNumberOfRooms.validateReservation - Start - Input: reservedDTO: {}", reservedDTO);
        Hotel hotel = hotelRepository.getReferenceById(reservedDTO.hotelId());

        if(hotel.getNumberOfRooms()<reservedDTO.numberOfRooms()) {
            throw new NumberOfRoomsException(ErrorCode.NUMBER_OF_ROOMS.getMessage());
        }
    }
}
