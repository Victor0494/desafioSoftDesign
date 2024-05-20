package br.com.desafio.ntconsult.validation;

import br.com.desafio.ntconsult.constant.ErrorCode;
import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import br.com.desafio.ntconsult.dto.GuestDTO;
import br.com.desafio.ntconsult.exception.InvalidGuestInformationException;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

public class ValidateGuestInformation implements ReservationValidation{

    @Override
    public void validateReservation(CreateReservationDTO reservedDTO) {
        GuestDTO guest = reservedDTO.guest();
        List<Object> fields = Arrays.asList(
                guest.name(),
                guest.email(),
                guest.phone(),
                guest.payment()
        );

        if(fields.stream().anyMatch(ObjectUtils::isEmpty)) {
            throw new InvalidGuestInformationException(ErrorCode.INVALID_GUEST_INFORMATION.getMessage());
        }

    }
}
