package br.com.desafio.ntconsult.entities;

import br.com.desafio.ntconsult.dto.CreateReservationDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hotelName;

    private Integer numberOfRooms;

    private Integer numberOfGuest;

    private String checkIn;

    private String checkOut;

    @OneToOne(cascade=CascadeType.PERSIST)
    private Guest responsibleGuest;

    public Reservation(CreateReservationDTO reservedDTO, Hotel hotel) {
        this.hotelName = hotel.getName();
        this.numberOfRooms = reservedDTO.numberOfRooms();
        this.numberOfGuest = reservedDTO.numberOfGuest();
        this.checkIn = reservedDTO.checkIn();
        this.checkOut = reservedDTO.checkOut();
        this.responsibleGuest = new Guest(reservedDTO.guest());
    }
}
