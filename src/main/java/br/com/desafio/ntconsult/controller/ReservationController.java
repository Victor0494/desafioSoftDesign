package br.com.desafio.ntconsult.controller;

import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.dto.ReservationDetailsDTO;
import br.com.desafio.ntconsult.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDetailsDTO> createReservation(@RequestBody @Valid CreateReservationDTO createReservationDTO, UriComponentsBuilder builder) {
        log.info("ReservationController.createReservation - Start - Input: createReservedDTO = {}", createReservationDTO);
        var response = reservationService.createReservation(createReservationDTO);

        var uri = builder.path("/hotel/reserved/{id}").buildAndExpand(response.id()).toUri();

        log.debug("ReservationController.createReservation - End - Input: createReservedDTO = {} - Output: Success", response.id());
        return ResponseEntity.created(uri).body(response);
    }
}
