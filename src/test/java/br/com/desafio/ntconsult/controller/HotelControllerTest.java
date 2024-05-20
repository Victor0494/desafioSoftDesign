package br.com.desafio.ntconsult.controller;

import br.com.desafio.ntconsult.service.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.verify;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Test
    @DisplayName("Return status code 200")
    void getHotelsFilteredByLocalization() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/hotel")
                        .param("localization", "Test")
                        .param("checkIn", "2024-11-18")
                        .param("checkOut", "2024-11-20")
                        .param("numberOfGuest", String.valueOf(1))
                        .param("numberOfRooms", String.valueOf(1))
                        .param("page", "10")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
        verify(hotelService).getHotelPage("Test", "2024-11-18", "2024-11-20", 1, 1, PageRequest.of(10, 10));
    }

    @Test
    @DisplayName("Return status code 200")
    void getHotelsCompared() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/hotel/compared")
                        .param("page", "10")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
        verify(hotelService).getHotelComparator(PageRequest.of(10, 10, Sort.by("name").ascending()));
    }
}