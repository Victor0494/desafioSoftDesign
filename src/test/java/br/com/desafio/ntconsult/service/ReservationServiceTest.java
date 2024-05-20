package br.com.desafio.ntconsult.service;

import br.com.desafio.ntconsult.constant.PaymentMethod;
import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.dto.GuestDTO;
import br.com.desafio.ntconsult.dto.PaymentsDetailsDTO;
import br.com.desafio.ntconsult.dto.ReservationDetailsDTO;
import br.com.desafio.ntconsult.entities.Hotel;
import br.com.desafio.ntconsult.exception.HotelNotFoundException;
import br.com.desafio.ntconsult.repository.HotelRepository;
import br.com.desafio.ntconsult.repository.ReservationRepository;
import br.com.desafio.ntconsult.validation.ReservationValidation;
import br.com.desafio.ntconsult.validation.ValidateCheckInDate;
import br.com.desafio.ntconsult.validation.ValidateGuestInformation;
import br.com.desafio.ntconsult.validation.ValidateNumberOfRooms;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.*;

import static br.com.desafio.ntconsult.constant.RabbitConstants.RESERVATION_EXCHANGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private List<ReservationValidation> validations;

    @Mock
    private ValidateCheckInDate validateCheckInDate;

    @Mock
    private ValidateGuestInformation validateGuestInformation;

    @Mock
    private ValidateNumberOfRooms validateNumberOfRooms;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private final String DATE = "2024-11-18";

    @Test
    @DisplayName("Create a reservation with success")
    void createReservationWithSuccess() {
        Hotel hotel = createHotel();
        CreateReservationDTO createReservationDTO = getCreateReservationDTO();
        when(hotelRepository.findById(1L)).thenReturn(Optional.ofNullable(hotel));

        ReservationDetailsDTO response = reservationService.createReservation(createReservationDTO);
        assertNotNull(response);
        assertEquals(response.hotelName(), hotel.getName());
        assertEquals(response.numberOfRooms(), createReservationDTO.numberOfRooms());
        assertEquals(response.checkIn(), createReservationDTO.checkIn());
        assertEquals(response.responsibleGuest(), createReservationDTO.guest());
    }

    @Test
    @DisplayName("Validate the hotel not found")
    void validateHotelNotFound() {
        assertThrows(HotelNotFoundException.class, () ->reservationService.createReservation(getCreateReservationDTO()));
    }

    private CreateReservationDTO getCreateReservationDTO() {
        return CreateReservationDTO.builder()
                .hotelId(1L)
                .numberOfRooms(1)
                .numberOfGuest(1)
                .checkIn(DATE)
                .guest(GuestDTO.builder().name("Victor").phone("999999").email("test@test")
                        .payment(PaymentsDetailsDTO.builder().paymentMethod(PaymentMethod.CREDIT).totalAmount(new BigDecimal(100)).build()).build()).build();
    }

    private Hotel createHotel() {
        List<String> list = new ArrayList<String>(Arrays.asList(DATE, DATE));

        return Hotel.builder()
                .name("Test")
                .id(1L)
                .numberOfGuest(5)
                .numberOfRooms(10)
                .checkIn(DATE).build();
    }
}