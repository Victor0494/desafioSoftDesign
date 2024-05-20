package br.com.desafio.ntconsult.entities;

import br.com.desafio.ntconsult.constant.Rating;
import br.com.desafio.ntconsult.dto.CreateHotelDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "hotels")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String localization;

    private String checkIn;

    private String checkOut;

    private Integer numberOfRooms;

    private Integer numberOfGuest;

    private BigDecimal pricePerNight;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "convenience_id")
    private Convenience convenience;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    public Hotel(CreateHotelDTO createHotelDTO) {
        this.name = createHotelDTO.name();
        this.localization = createHotelDTO.localization();
        this.checkIn = createHotelDTO.checkIn();
        this.checkOut = createHotelDTO.checkOut();
        this.numberOfRooms = createHotelDTO.numberOfRooms();
        this.numberOfGuest = createHotelDTO.numberOfGuest();
        this.pricePerNight = createHotelDTO.pricePerNight();
        this.convenience = new Convenience(createHotelDTO.convenience());
        this.rating = createHotelDTO.rating();
    }

    public void updateNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms -= numberOfRooms;
    }

    public void updateCheckInAvailable(String checkIn) {
        List<String> list = new ArrayList<>(Arrays.asList(this.checkIn.split(",")));
        list.removeIf(checkInlist -> checkInlist.contains(checkIn));
        this.checkIn = String.join(",", list);
    }

    public void updateNumberOfGuest(Integer numberOfGuest) {
        this.numberOfGuest += numberOfGuest;
    }

}
