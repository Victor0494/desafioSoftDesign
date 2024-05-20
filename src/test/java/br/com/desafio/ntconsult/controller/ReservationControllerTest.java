package br.com.desafio.ntconsult.controller;

import br.com.desafio.ntconsult.constant.PaymentMethod;
import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.dto.GuestDTO;
import br.com.desafio.ntconsult.dto.PaymentsDetailsDTO;
import br.com.desafio.ntconsult.dto.ReservationDetailsDTO;
import br.com.desafio.ntconsult.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ReservationControllerTest {

    public static final String LOCAL_DATE_TIME = "2024-11-18";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private JacksonTester<CreateReservationDTO> createReservationDTOJacksonTester;

    @Autowired
    private JacksonTester<ReservationDetailsDTO> reservationDetailsDTOJacksonTester;

    @Test
    @DisplayName("Create a reservation with success")
    void createReservationWithSuccess() throws Exception {
        CreateReservationDTO createReservationDTO = getReservationDTO();
        ReservationDetailsDTO reservationDetailsDTO = getReservationDetailsDTO();
        String json = reservationDetailsDTOJacksonTester.write(reservationDetailsDTO).getJson();

        when(reservationService.createReservation(createReservationDTO)).thenReturn(reservationDetailsDTO);

        MockHttpServletResponse response = mockMvc.perform(
                post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createReservationDTOJacksonTester.write(createReservationDTO).getJson())
        ).andReturn().getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertThat(response.getContentAsString()).isEqualTo(json);
    }

    @Test
    @DisplayName("Create a reservation without payload")
    void createReservationWithInvalidInformation() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Create a reservation without number of rooms")
    void createReservationWithoutNumberOfRooms() throws Exception {
        CreateReservationDTO createReservationDTO = CreateReservationDTO.builder().hotelId(1L).numberOfRooms(null).build();

        MockHttpServletResponse response = mockMvc.perform(
                post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createReservationDTOJacksonTester.write(createReservationDTO).getJson())
        ).andReturn().getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Create a reservation without checkIn date")
    void createReservationWithoutCheckIn() throws Exception {
        CreateReservationDTO createReservationDTO = CreateReservationDTO.builder().hotelId(1L).numberOfRooms(2).checkIn(null).build();

        MockHttpServletResponse response = mockMvc.perform(
                post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createReservationDTOJacksonTester.write(createReservationDTO).getJson())
        ).andReturn().getResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    private static ReservationDetailsDTO getReservationDetailsDTO() {
        return ReservationDetailsDTO.builder().id(1L).hotelName("Test").checkIn(LOCAL_DATE_TIME)
                .numberOfRooms(1)
                .responsibleGuest(GuestDTO.builder().name("Test").email("teste@email.com").phone("999999")
                        .payment(PaymentsDetailsDTO.builder().paymentMethod(PaymentMethod.CREDIT).totalAmount(new BigDecimal(100)).build()).build()).build();
    }

    private static CreateReservationDTO getReservationDTO() {
        return CreateReservationDTO.builder()
                .hotelId(1L)
                .numberOfRooms(1)
                .numberOfGuest(1)
                .checkIn(LOCAL_DATE_TIME)
                .checkOut(LOCAL_DATE_TIME)
                .guest(GuestDTO.builder().name("Test").email("teste@email.com").phone("999999")
                        .payment(PaymentsDetailsDTO.builder().paymentMethod(PaymentMethod.CREDIT).totalAmount(new BigDecimal(100)).build()).build()).build();
    }
}