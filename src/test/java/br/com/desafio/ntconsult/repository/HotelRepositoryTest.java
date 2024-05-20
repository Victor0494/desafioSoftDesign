package br.com.desafio.ntconsult.repository;

import br.com.desafio.ntconsult.constant.Rating;
import br.com.desafio.ntconsult.entities.Convenience;
import br.com.desafio.ntconsult.entities.Hotel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("test")
class HotelRepositoryTest {

    public static final String LOCALIZATION = "São Paulo";

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void getHotelFiltersByLocalization() {
        Page<Hotel> hotelPage = hotelRepository.getHotelFilters(LOCALIZATION, null, null, null, null, PageRequest.of(0, 10));
        List<Hotel> hotels = hotelPage.getContent();
        assertEquals(hotels.get(0).getLocalization(), LOCALIZATION);
    }

    @Test
    void getHotelFiltersByNumberOfRooms() {
        Page<Hotel> hotelPage = hotelRepository.getHotelFilters(null, 20, null, null, null, PageRequest.of(0, 10));
        List<Hotel> hotels = hotelPage.getContent();
        assertEquals(hotels.get(0).getNumberOfRooms(), 20);
    }

    @Test
    void getHotelFiltersByNumberOfGuest() {
        Page<Hotel> hotelPage = hotelRepository.getHotelFilters(null, null, 5, null, null, PageRequest.of(0, 10));
        List<Hotel> hotels = hotelPage.getContent();
        assertEquals(hotels.get(0).getNumberOfGuest(), 5);
    }

    @Test
    void getHotelFiltersByCheckIn() {
        String checkIn = "19/11/2024";
        Page<Hotel> hotelPage = hotelRepository.getHotelFilters(null, null, null, checkIn, null, PageRequest.of(0, 10));
        List<Hotel> hotels = hotelPage.getContent();
        assertTrue(hotels.get(0).getCheckIn().contains(checkIn));
    }

    @Test
    void getHotelFiltersByCheckOut() {
        String checkOut = "23/02/2024";
        Page<Hotel> hotelPage = hotelRepository.getHotelFilters(null, null, null, null, checkOut, PageRequest.of(0, 10));
        List<Hotel> hotels = hotelPage.getContent();
        assertTrue(hotels.get(0).getCheckOut().contains(checkOut));
    }

    @Test
    void getHotelWithAllFilters() {

        Page<Hotel> hotelPage = hotelRepository.getHotelFilters(LOCALIZATION, 10, 5, "18/11/2024", "22/02/2024", PageRequest.of(0, 10));
        List<Hotel> hotels = hotelPage.getContent();
        assertEquals(hotels.get(0).getLocalization(), LOCALIZATION);
        assertEquals(hotels.get(0).getNumberOfRooms(), 10);
        assertEquals(hotels.get(0).getNumberOfGuest(), 5);
        assertTrue(hotels.get(0).getCheckIn().contains("18/11/2024"));
        assertTrue(hotels.get(0).getCheckOut().contains("23/02/2024"));
    }

    @Test
    void getHotelWithOutFilters() {
        Page<Hotel> hotelPage = hotelRepository.getHotelFilters(null, null, null, null, null, PageRequest.of(0, 10));
        List<Hotel> hotels = hotelPage.getContent();
        assertFalse(ObjectUtils.isEmpty(hotelPage.getContent()));
       assertEquals(hotelPage.getContent().size(), 3);
    }

}