package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.entities.Guest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record GuestDTO(@NotBlank String name, @NotBlank String phone, @NotBlank String document, @NotBlank String email, PaymentsDetailsDTO payment) {

    public GuestDTO(Guest responsibleGuest) {
        this(responsibleGuest.getName(), responsibleGuest.getPhone(), responsibleGuest.getDocument(), responsibleGuest.getEmail(), new PaymentsDetailsDTO(responsibleGuest.getPaymentDetails()));
    }

}
