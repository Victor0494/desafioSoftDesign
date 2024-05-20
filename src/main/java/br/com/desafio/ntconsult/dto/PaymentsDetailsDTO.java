package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.constant.PaymentMethod;
import br.com.desafio.ntconsult.entities.PaymentDetails;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentsDetailsDTO(PaymentMethod paymentMethod, BigDecimal totalAmount) {

    public PaymentsDetailsDTO(PaymentDetails paymentDetails) {
        this(paymentDetails.getPaymentMethod(), paymentDetails.getAmount());
    }
}
