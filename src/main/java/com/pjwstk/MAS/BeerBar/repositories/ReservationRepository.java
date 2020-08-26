package com.pjwstk.MAS.BeerBar.repositories;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r where r.bar = :bar AND year(r.startTime) = :year AND month(r.startTime) = :month AND day(r.startTime) = :day")
    List<Reservation> getReservationsForDateAndBar(@Param("bar") Bar bar, @Param("year") int year, @Param("month") int month, @Param("day") int day);
}
