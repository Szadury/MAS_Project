package com.pjwstk.MAS.BeerBar.services;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.models.Reservation;
import com.pjwstk.MAS.BeerBar.repositories.ReservationRepository;
import com.pjwstk.MAS.BeerBar.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> findAvailableReservationsByDateAndBar(String date, Bar bar) {
        int year = DateUtils.getYearFromDate(date);
        int month = DateUtils.getMonthFromDate(date);
        int day = DateUtils.getDayFromDate(date);

        List<BarTable> barTables = bar.getBarTables();
        List<Reservation> existingReservations = getExistingReservationsByDateAndBarTables(barTables, year, month, day);
        List<Reservation> availableReservations = new ArrayList<>();
        for (BarTable bt : barTables) {
            int currentReservationStartHour = bar.getStartHour();
            int barEndTime = bar.getEndHour();
            int hourOfLastPossibleReservation = barEndTime - Reservation.getReservationTime();
            while (currentReservationStartHour <= hourOfLastPossibleReservation) {
                LocalDateTime startingReservationTime = DateUtils.createLocalDateTime(year, month, day, currentReservationStartHour);
                LocalDateTime endingReservationTime = startingReservationTime.plusHours(Reservation.getReservationTime());
                if (!reservationForGivenHourExists(bt, existingReservations, startingReservationTime, endingReservationTime)) {
                    Reservation reservation = new Reservation();
                    reservation.setBarTable(bt);
                    reservation.setStartTime(startingReservationTime);
                    reservation.setEndTime(endingReservationTime);
                    availableReservations.add(reservation);
                }
                currentReservationStartHour += 1;
            }
        }
        return availableReservations;
    }

    private List<Reservation> getExistingReservationsByDateAndBarTables(List<BarTable> barTables, int year, int month, int day) {
        List<Reservation> currentReservations = new ArrayList<>();
        for (BarTable bt : barTables) {
            currentReservations.addAll(getReservationsOfTableByDate(bt, year, month, day));
        }
        return currentReservations;
    }

    private List<Reservation> getReservationsOfTableByDate(BarTable bt, int year, int month, int day) {
        return bt.getReservations().stream().filter(reservation ->
                reservation.getStartTime().getYear() == year &&
                        reservation.getStartTime().getMonth().getValue() == month &&
                        reservation.getStartTime().getDayOfMonth() == day).collect(Collectors.toList());
    }

    public List<Reservation> findAvailableReservationPerSeat(int seatId, List<Reservation> availableReservations) {
        List<Reservation> availableTableReservations = new ArrayList<>();
        for (Reservation res : availableReservations) {
            if (res.getBarTable().getId() == seatId) {
                availableTableReservations.add(res);
            }
        }
        return availableTableReservations;
    }

    private boolean reservationForGivenHourExists(BarTable bt, List<Reservation> currentReservations, LocalDateTime givenHourTime, LocalDateTime endHourTime) {
        for (Reservation reservation : currentReservations) {
            if (reservation.getBarTable().getId() == bt.getId()) {
                int startHour = givenHourTime.toLocalTime().getHour();
                int endHour = endHourTime.toLocalTime().getHour();
                int reservationStartHour = reservation.getStartTime().toLocalTime().getHour();
                int reservationEndHour = reservation.getEndTime().toLocalTime().getHour();
                if (endHour == 0)
                    endHour = 24;
                if (reservationEndHour == 0)
                    reservationEndHour = 24;

                if ((startHour >= reservationStartHour && startHour < reservationEndHour) ||
                        (reservationStartHour >= startHour && reservationStartHour < endHour)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<BarTable> findAvailableSeatsByAvailableReservations(List<Reservation> availableReservations) {
        List<BarTable> availableSeats = new ArrayList<>();
        for (Reservation reservation : availableReservations) {
            BarTable bt = reservation.getBarTable();
            if (!availableSeats.contains(bt)) {
                availableSeats.add(bt);
            }
        }
        return availableSeats;
    }
}
