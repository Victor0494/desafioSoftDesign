package br.com.desafio.notification.dto;


import br.com.desafio.notification.constant.PaymentMethod;

import java.math.BigDecimal;

public record PaymentsDetailsDTO(PaymentMethod paymentMethod, BigDecimal totalAmount) {

    @Override
    public String toString() {
        return"Método de pagamento= " + paymentMethod +
                ", Valor total= " + totalAmount;
    }
}
