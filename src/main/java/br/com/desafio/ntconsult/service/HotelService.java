package br.com.desafio.ntconsult.service;

import br.com.desafio.ntconsult.dto.CreateHotelDTO;
import br.com.desafio.ntconsult.dto.CreateHotelDetailsDTO;
import br.com.desafio.ntconsult.dto.ListHotelsDTO;
import br.com.desafio.ntconsult.entities.Hotel;
import br.com.desafio.ntconsult.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;

    public CreateHotelDetailsDTO createHotel(CreateHotelDTO createHotelDTO) {
        log.info("HotelService.createHotel - Start - Input: HotelName: {}", createHotelDTO.name());
        log.debug("HotelService.createHotel - Start - Input: createHotelDTO: {}", createHotelDTO);

        Hotel hotel = new Hotel(createHotelDTO);

        hotelRepository.save(hotel);
        log.debug("HotelService.createHotel - End - Input: createHotelDTO: {}", createHotelDTO);
        return new CreateHotelDetailsDTO(hotel);
    }

    public Page<ListHotelsDTO> getHotelPage(String localization, String checkIn, String checkOut, Integer numberOfRooms, Integer numberOfGuest, Pageable pageable) {
        log.info("HotelService.getHotelList - Start - Input: localization: {}, checkIn: {}, checkOut: {}, numberOfRooms: {}, numberOfGuest: {}, pageable: {}",
                localization, checkIn, checkOut, numberOfRooms, numberOfGuest, pageable);

        Page<ListHotelsDTO> response = hotelRepository.getHotelFilters(localization ,numberOfRooms, numberOfGuest, checkIn, checkOut, pageable)
                .map(ListHotelsDTO::new);

        log.debug("HotelService.getHotelList - End - Input: ListHotelsDTO: {}", response.getContent());
        return response;
    }

    public Page<ListHotelsDTO> getHotelComparator(Pageable pageable) {
        log.info("HotelService.getHotelComparator - Start - Input: pageable: {}", pageable);

        Page<ListHotelsDTO> response = hotelRepository.findAll(pageable).map(ListHotelsDTO::new);

        log.debug("HotelService.getHotelComparator - End - Input: ListHotelsDTO: {}", response.getContent());
        return response;
    }

//    Page<ListHotelsDTO> response = hotelRepository.getHotelFilters(localization,checkIn, checkOut, numberOfRooms, numberOfGuest, pageable)

}
