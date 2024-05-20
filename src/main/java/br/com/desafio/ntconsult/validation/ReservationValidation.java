package br.com.desafio.ntconsult.validation;

import br.com.desafio.ntconsult.dto.CreateReservationDTO;

public interface ReservationValidation {

    void validateReservation(CreateReservationDTO reservedDTO);
}
