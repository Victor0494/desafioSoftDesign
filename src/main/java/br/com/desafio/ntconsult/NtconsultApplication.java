package br.com.desafio.ntconsult;

import br.com.desafio.ntconsult.constant.Rating;
import br.com.desafio.ntconsult.entities.Convenience;
import br.com.desafio.ntconsult.entities.Hotel;
import br.com.desafio.ntconsult.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;

@SpringBootApplication
public class NtconsultApplication implements CommandLineRunner {

	public NtconsultApplication(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(NtconsultApplication.class, args);
	}

	private final HotelRepository hotelRepository;

	@Override
	public void run(String... args) throws Exception {
		hotelRepository.deleteAll();
		hotelRepository.save(Hotel.builder()
				.name("Hotel 1")
				.localization("São Paulo")
				.checkIn("18/11/2024, 19/12/2024, 20/02/2024, 21/05/2024")
				.checkOut("22/02/2024, 23/02/2024, 25/02/2024, 28/02/2024")
				.numberOfRooms(10)
				.numberOfGuest(2)
				.pricePerNight(new BigDecimal("150"))
				.convenience(Convenience.builder().parking(false).wifi(false).airConditioning(false).tv(false).build())
				.rating(Rating.TWO_STAR)
				.build());
		hotelRepository.save(Hotel.builder()
				.name("Hotel 2")
				.localization("Rio de Janeiro")
				.checkIn("18/11/2024, 19/12/2024, 20/08/2024, 21/05/2024")
				.checkOut("22/02/2024, 23/02/2024, 25/02/2024, 28/02/2024")
				.numberOfRooms(20)
				.numberOfGuest(2)
				.pricePerNight(new BigDecimal("100"))
				.convenience(Convenience.builder().parking(true).wifi(false).airConditioning(false).tv(false).build())
				.rating(Rating.THREE_STAR)
				.build());
		hotelRepository.save(Hotel.builder()
				.name("Hotel 3")
				.localization("Curitiba")
				.checkIn("18/11/2024, 19/12/2024, 20/08/2024, 21/05/2024")
				.checkOut("22/02/2024, 23/02/2024, 25/02/2024, 28/02/2024")
				.numberOfRooms(50)
				.numberOfGuest(2)
				.pricePerNight(new BigDecimal("50"))
				.convenience(Convenience.builder().parking(true).wifi(true).airConditioning(true).tv(false).build())
				.rating(Rating.FIVE_STAR)
				.build());


	}
}
