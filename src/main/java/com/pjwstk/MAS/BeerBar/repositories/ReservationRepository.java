package com.pjwstk.MAS.BeerBar.repositories;

import com.pjwstk.MAS.BeerBar.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT * FROM Reservation r where r.bar_id = :barId AND EXTRACT(YEAR FROM r.startTime) = :year AND EXTRACT(MONTH FROM r.startTime) = :month AND EXTRACT(DAY FROM r.startTime) = :day", nativeQuery = true)
    Iterable<Reservation> getReservationsForDateAndBar(@Param("barId") int barId, @Param("year") int year, @Param("month") int month, @Param("day") int day);
}
