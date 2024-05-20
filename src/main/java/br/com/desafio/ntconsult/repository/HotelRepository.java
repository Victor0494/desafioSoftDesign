package br.com.desafio.ntconsult.repository;

import br.com.desafio.ntconsult.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("select h from Hotel h " +
            "where (h.localization = :localization or :localization is null) " +
            "and (h.numberOfRooms = :numberOfRooms or :numberOfRooms is null) " +
            "and (h.numberOfGuest = :numberOfGuest or :numberOfGuest is null) " +
            "and (:checkIn is null or h.checkIn like :checkIn%) " +
            "and (:checkOut is null or h.checkOut like :checkOut%)" )
    Page<Hotel> getHotelFilters(String localization, Integer numberOfRooms, Integer numberOfGuest , String checkIn, String checkOut, Pageable pageable);
}
