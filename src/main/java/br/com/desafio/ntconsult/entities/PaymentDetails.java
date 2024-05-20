package br.com.desafio.ntconsult.entities;

import br.com.desafio.ntconsult.constant.PaymentMethod;
import br.com.desafio.ntconsult.dto.PaymentsDetailsDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private BigDecimal amount;

    public PaymentDetails(PaymentsDetailsDTO payment) {
        this.paymentMethod = payment.paymentMethod();
        this.amount = payment.totalAmount();
    }
}
