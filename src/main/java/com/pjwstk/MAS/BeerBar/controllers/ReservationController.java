package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.models.Reservation;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BarTableRepository;
import com.pjwstk.MAS.BeerBar.repositories.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {

    Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    BarRepository barRepository;

    @Autowired
    BarTableRepository barTableRepositoryRepository;

    @PostMapping("/findSeats")
    public String findSeats(@RequestParam(name = "reservation-date") String date, @RequestParam(name = "barId") int barId, Model model) {
        List<Reservation> availableReservations = new ArrayList<>();
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        Bar bar = barRepository.getBarById(barId);
        Iterable<BarTable> barTables = barTableRepositoryRepository.getBarTablesByBarID(barId);
        Iterable<Reservation> reservationIterable = reservationRepository.getReservationsForDateAndBar(barId, year, month, day);
        List<Reservation> currentReservations = new ArrayList<>();
        reservationIterable.forEach(currentReservations::add);
        for (BarTable bt : barTables) {
            int startTime = bar.getStartHour();
            int barEndTime = bar.getEndHour();
            while (startTime <= barEndTime - Reservation.reservationTime + 1) {
                LocalDateTime givenHourTime = getHourTime(year, month, day, startTime);
                if (reservationForGivenHourExists(bt, currentReservations, givenHourTime)) {
                    startTime += 1;
                } else {
                    Reservation reservation = new Reservation();
                    reservation.setBar(bar);
                    reservation.setBarTable(bt);
                    reservation.setStartTime(givenHourTime);
                    reservation.setEndTime(givenHourTime.plusHours(Reservation.reservationTime));
                    startTime += 1;
                    availableReservations.add(reservation);
                }
            }
        }
        model.addAttribute("availableReservations", availableReservations);
        model.addAttribute("barId", barId);
        return "availableReservations";
    }

    @PostMapping("/createReservation")
    public String createReservation(@RequestParam(name = "startTime") String startTimeString, @RequestParam(name = "endTime") String endTimeString,
                                    @RequestParam(name = "barId") int barId, @RequestParam(name = "tableId") int tableId, Model model, HttpStatus httpStatus) {
        LocalDateTime startTime = LocalDateTime.parse(startTimeString);
        LocalDateTime endTime = LocalDateTime.parse(endTimeString);
        logger.info(String.valueOf(startTime));
        logger.info(String.valueOf(endTime));
        logger.info(String.valueOf(barId));
        return "login";
    }
    private boolean reservationForGivenHourExists(BarTable bt, List<Reservation> currentReservations, LocalDateTime givenHourTime) {
        for (Reservation reservation : currentReservations) {
            if (reservation.getBarTable() == bt) {
                if (givenHourTime.toLocalTime().getHour() >= reservation.getStartTime().toLocalTime().getHour() &&
                        givenHourTime.toLocalTime().getHour() < reservation.getEndTime().toLocalTime().getHour()) {
                    return true;
                }
            }
        }
        return false;
    }

    private LocalDateTime getHourTime(int year, int month, int day, int startTime) {
        LocalDate ld = LocalDate.of(year, month, day);
        LocalTime lt = LocalTime.of(startTime, 0);
        return LocalDateTime.of(ld, lt);
    }
}
