package br.com.desafio.ntconsult.validation;

import br.com.desafio.ntconsult.constant.PaymentMethod;
import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.dto.GuestDTO;
import br.com.desafio.ntconsult.exception.InvalidGuestInformationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateGuestInformationTest {

    @InjectMocks
    private ValidateGuestInformation validateGuestInformation;

    @Test
    void validateReservation() {

        assertThrows(InvalidGuestInformationException.class, () ->validateGuestInformation.validateReservation(getCreateReservationDTO()));
    }

    private CreateReservationDTO getCreateReservationDTO() {
        return CreateReservationDTO.builder()
                .hotelId(1L)
                .numberOfRooms(1)
                .checkIn("2024-11-18")
                .guest(GuestDTO.builder().build()).build();
    }
}