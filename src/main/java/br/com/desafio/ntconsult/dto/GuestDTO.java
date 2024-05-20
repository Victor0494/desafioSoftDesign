package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.entities.Guest;
import lombok.Builder;

@Builder
public record GuestDTO(String name, String phone, String email, PaymentsDetailsDTO payment) {

    public GuestDTO(Guest responsibleGuest) {
        this(responsibleGuest.getName(), responsibleGuest.getPhone(), responsibleGuest.getEmail(), new PaymentsDetailsDTO(responsibleGuest.getPaymentDetails()));
    }

}
