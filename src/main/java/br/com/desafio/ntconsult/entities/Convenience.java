package br.com.desafio.ntconsult.entities;

import br.com.desafio.ntconsult.dto.ConvenienceDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conveniences")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Convenience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean wifi;

    private Boolean airConditioning;

    private Boolean tv;

    private Boolean parking;

    public Convenience(ConvenienceDTO convenience) {
        this.wifi = convenience.wifi();
        this.airConditioning = convenience.airConditioning();
        this.tv = convenience.tv();
        this.parking = convenience.parking();
    }
}
