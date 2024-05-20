package br.com.desafio.ntconsult.validation;

import br.com.desafio.ntconsult.constant.PaymentMethod;
import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.dto.GuestDTO;
import br.com.desafio.ntconsult.dto.PaymentsDetailsDTO;
import br.com.desafio.ntconsult.entities.Hotel;
import br.com.desafio.ntconsult.exception.DateAlreadyReservedException;
import br.com.desafio.ntconsult.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateCheckInDateTest {

    @InjectMocks
    private ValidateCheckInDate validateCheckInDate;

    public static final String LOCAL_DATE = "2024-11-18";

    @Mock
    private HotelRepository hotelRepository;

    @Test
    void validateReservation() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.ofNullable(createHotel()));

       assertThrows(DateAlreadyReservedException.class, () -> validateCheckInDate.validateReservation(getCreateReservationDTO()));
    }

    private CreateReservationDTO getCreateReservationDTO() {
        return CreateReservationDTO.builder()
                .hotelId(1L)
                .numberOfRooms(5)
                .checkIn(LOCAL_DATE)
                .guest(GuestDTO.builder().name("Victor").phone("999999").email("test@test")
                        .payment(PaymentsDetailsDTO.builder().paymentMethod(PaymentMethod.CREDIT).totalAmount(new BigDecimal(100)).build()).build()).build();
    }

    private Hotel createHotel() {
        return Hotel.builder()
                .name("Test")
                .id(1L)
                .numberOfGuest(5)
                .numberOfRooms(1)
                .checkIn("2024-02-24").build();
    }
}