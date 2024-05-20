package br.com.desafio.ntconsult.service;

import br.com.desafio.ntconsult.dto.ListHotelsDTO;
import br.com.desafio.ntconsult.entities.Convenience;
import br.com.desafio.ntconsult.entities.Hotel;
import br.com.desafio.ntconsult.repository.HotelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @InjectMocks
    HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    public static final String LOCALIZATION =  "Chapada";

    public static final String LOCAL_DATE_TIME = "2024-11-18";

    @Test
    @DisplayName("Get page of hotel")
    void getHotelPage() {
        Pageable pageable = PageRequest.of(0, 10);
        List<String> StringList = Collections.singletonList(LOCAL_DATE_TIME);
        Hotel hotel = getHotel();

        when(hotelRepository.getHotelFilters(eq(LOCALIZATION), eq(1), eq(1), eq(LOCAL_DATE_TIME), eq(LOCAL_DATE_TIME), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(hotel)));

        Page<ListHotelsDTO> response = hotelService.getHotelPage(LOCALIZATION, LOCAL_DATE_TIME, LOCAL_DATE_TIME, 1, 1, pageable);
        assertNotNull(response);
        assertEquals(response.getTotalElements(), 1);
        assertEquals(hotel.getLocalization(), response.getContent().get(0).localization());
        assertEquals(hotel.getName(), response.getContent().get(0).name());
        assertEquals(hotel.getCheckIn(), response.getContent().get(0).checkIn());
        assertEquals(hotel.getCheckOut(), response.getContent().get(0).checkOut());
        assertEquals(hotel.getNumberOfRooms(), response.getContent().get(0).numberOfRooms());
        assertEquals(hotel.getNumberOfGuest(), response.getContent().get(0).numberOfGuest());
        verify(hotelRepository, only()).getHotelFilters(LOCALIZATION, 1 , 1, LOCAL_DATE_TIME, LOCAL_DATE_TIME, pageable);
    }

    @Test
    @DisplayName("Get page of hotel without filter")
    void getHotelPageWithoutFilters() {
        assertThrows(NullPointerException.class, () ->hotelService.getHotelPage(null, null, null, null, null, null));
    }

    @Test
    @DisplayName("Get page of hotel with pageable")
    void getHotelWithPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        Hotel hotel = getHotel();

        when(hotelRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(hotel)));

        Page<ListHotelsDTO> response = hotelService.getHotelComparator(pageable);
        assertNotNull(response);
        assertEquals(response.getTotalElements(), 1);
        assertEquals(hotel.getLocalization(), response.getContent().get(0).localization());
        assertEquals(hotel.getName(), response.getContent().get(0).name());
        assertEquals(hotel.getCheckIn(), response.getContent().get(0).checkIn());
        assertEquals(hotel.getCheckOut(), response.getContent().get(0).checkOut());
        assertEquals(hotel.getNumberOfRooms(), response.getContent().get(0).numberOfRooms());
        assertEquals(hotel.getNumberOfGuest(), response.getContent().get(0).numberOfGuest());
        verify(hotelRepository, only()).findAll(pageable);
    }

    @Test
    @DisplayName("Get page of hotel with pageable")
    void getHotelWithoutPageable() {
       assertThrows(NullPointerException.class, () ->hotelService.getHotelComparator(null));

    }

    private Hotel getHotel() {
        return Hotel.builder().localization(LOCALIZATION)
                .name("Hotel")
                .checkIn(LOCAL_DATE_TIME)
                .checkOut(LOCAL_DATE_TIME)
                .numberOfRooms(1)
                .numberOfGuest(1)
                .convenience(Convenience.builder().tv(true).wifi(true).airConditioning(false).parking(true).build())
                .build();
    }
}