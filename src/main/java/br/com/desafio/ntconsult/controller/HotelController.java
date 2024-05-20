package br.com.desafio.ntconsult.controller;

import br.com.desafio.ntconsult.dto.CreateHotelDTO;
import br.com.desafio.ntconsult.dto.CreateHotelDetailsDTO;
import br.com.desafio.ntconsult.dto.ListHotelsDTO;
import br.com.desafio.ntconsult.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<CreateHotelDetailsDTO> createHotel(@RequestBody CreateHotelDTO createHotelDTO, UriComponentsBuilder builder) {
        log.info("HotelController.createHotel - Start - Input: createHotelDTO = {}", createHotelDTO);

        var response = hotelService.createHotel(createHotelDTO);
        var uri = builder.path("/hotel/{id}").buildAndExpand(response.id()).toUri();

        log.debug("HotelController.createHotel - End - Input: Input: createHotelDTO = {} - Output: Success", response.id());
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ListHotelsDTO>> getHotels(@RequestParam(value = "localization", required = false) String localization,
                                                         @RequestParam(value = "checkIn", required = false, defaultValue = "") String checkIn,
                                                         @RequestParam(value = "checkOut", required = false, defaultValue = "") String checkOut,
                                                         @RequestParam(value = "numberOfRooms", required = false) Integer numberOfRooms,
                                                         @RequestParam(value = "numberOfGuest", required = false) Integer numberOfGuest,
                                                         @PageableDefault(size = 10) Pageable pageable) {
        log.info("HotelController.getHotels - Start");

        var response = hotelService.getHotelPage(localization, checkIn, checkOut, numberOfRooms, numberOfGuest, pageable);
        log.debug("HotelController.getHotels - End - Input: Input: ListHotelsDTO = {} - Output: Success", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/compared")
    public ResponseEntity<Page<ListHotelsDTO>> getHotelsCompared(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        log.info("HotelController.getHotelsCompared - Start - Input: pageable = {}", pageable);

        var response = hotelService.getHotelComparator(pageable);

        log.debug("HotelController.getHotelsCompared - End - Input: Input: ListHotelsDTO = {} - Output: Success", response);
        return ResponseEntity.ok(response);
    }


}
