package br.com.desafio.ntconsult.service;

import br.com.desafio.ntconsult.constant.ErrorCode;
import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.dto.ReservationDetailsDTO;
import br.com.desafio.ntconsult.entities.Hotel;
import br.com.desafio.ntconsult.entities.Reservation;
import br.com.desafio.ntconsult.exception.HotelNotFoundException;
import br.com.desafio.ntconsult.repository.HotelRepository;
import br.com.desafio.ntconsult.repository.ReservationRepository;
import br.com.desafio.ntconsult.validation.ReservationValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.desafio.ntconsult.constant.RabbitConstants.RESERVATION_EXCHANGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final HotelRepository hotelRepository;

    private final List<ReservationValidation> validations;

    private final RabbitTemplate rabbitTemplate;

    public ReservationDetailsDTO createReservation(CreateReservationDTO reservedDTO) {
        log.info("ReservationService.createReservation - Start - Input: reservedDTO: {}", reservedDTO);
        log.debug("ReservationService.createReservation - Start - Input: reservedDTO: {}", reservedDTO);

        validations.forEach(reservationValidation -> reservationValidation.validateReservation(reservedDTO));

        Optional<Hotel> hotel = hotelRepository.findById(reservedDTO.hotelId());
        if(hotel.isEmpty()) {
            throw new HotelNotFoundException(ErrorCode.HOTEL_NOT_FOUND.getMessage());
        }

        updateHotelInfo(hotel.get(), reservedDTO);

        Reservation reservation = new Reservation(reservedDTO, hotel.get());
        reservationRepository.save(reservation);
        ReservationDetailsDTO reservationDetailsDTO = new ReservationDetailsDTO(reservation);

        rabbitTemplate.convertAndSend(RESERVATION_EXCHANGE,"", reservationDetailsDTO);

        log.info("ReservationService.createReservation - End - Input: ReservationDetailsDTO: {}", reservationDetailsDTO);
        return reservationDetailsDTO;
    }

    private void updateHotelInfo(Hotel hotel, CreateReservationDTO reservedDTO) {
        hotel.updateNumberOfRooms(reservedDTO.numberOfRooms());

        if(hotel.getNumberOfRooms() <= 0) {
            hotel.updateCheckInAvailable(reservedDTO.checkIn());
        }
        hotel.updateNumberOfGuest(reservedDTO.numberOfGuest());
    }


}
