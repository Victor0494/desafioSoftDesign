package br.com.desafio.ntconsult.entities;

import br.com.desafio.ntconsult.dto.GuestDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "guests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String email;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_details_id")
    private PaymentDetails paymentDetails;

    public Guest(GuestDTO guest) {
        this.name = guest.name();
        this.phone = guest.phone();
        this.email = guest.email();
        this.paymentDetails = new PaymentDetails(guest.payment());
    }
}
