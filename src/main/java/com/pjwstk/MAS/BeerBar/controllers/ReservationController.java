package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.models.PremiumUser;
import com.pjwstk.MAS.BeerBar.models.Reservation;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BarTableRepository;
import com.pjwstk.MAS.BeerBar.repositories.PremiumUserRepository;
import com.pjwstk.MAS.BeerBar.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    BarRepository barRepository;

    @Autowired
    BarTableRepository barTableRepositoryRepository;

    @Autowired
    PremiumUserRepository premiumUserRepository;

    @PostMapping("/findSeats")
    public String findSeats(@RequestParam(name = "reservation-date") String date, @RequestParam(name = "barId") int barId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        } else {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));

            Bar bar = barRepository.getBarById(barId);

            Iterable<BarTable> barTables = barTableRepositoryRepository.getBarTablesByBarID(barId);
            Iterable<Reservation> reservationIterable = reservationRepository.getReservationsForDateAndBar(barId, year, month, day);

            List<Reservation> currentReservations = new ArrayList<>();
            reservationIterable.forEach(currentReservations::add);

            List<Reservation> availableReservations = findAvailableReservation(barTables, bar, currentReservations, year, month, day);//new ArrayList<>();
            if (!availableReservations.isEmpty()) {
                model.addAttribute("availableReservations", availableReservations);
                model.addAttribute("barId", barId);
                return "availableReservations";
            } else {
                redirectAttributes.addAttribute("barId", barId);
                redirectAttributes.addAttribute("NoReservations", "True");
                return "redirect:/bar/hasSeats";
            }
        }
    }

    @PostMapping("/createReservation")
    public String createReservation(@RequestParam(name = "startTime") String startTimeString, @RequestParam(name = "endTime") String endTimeString,
                                    @RequestParam(name = "barId") int barId, @RequestParam(name = "tableId") int tableId, Model model, HttpSession session) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        } else {
            Bar bar = barRepository.getBarById(barId);
            BarTable barTable = barTableRepositoryRepository.getById(tableId);
            LocalDateTime startTime = LocalDateTime.parse(startTimeString);
            LocalDateTime endTime = LocalDateTime.parse(endTimeString);
            if (bar != null && barTable != null) {
                int userModelId = (int) session.getAttribute("id");
                PremiumUser premiumUser = findPremiumUserByUserModel(userModelId);
                if (premiumUser != null) {
                    Reservation reservation = new Reservation();
                    reservation.setUser(premiumUser);
                    reservation.setStatus(Reservation.StatusType.Pending);
                    reservation.setBar(bar);
                    reservation.setBarTable(barTable);
                    reservation.setStartTime(startTime);
                    reservation.setEndTime(endTime);
                    reservationRepository.save(reservation);
                    return "successfulReservation";
                } else {
                    return "redirect:/bar/bars";
                }
            } else {
                return "redirect:reservation/findSeats";
            }
        }
    }

    private List<Reservation> findAvailableReservation(Iterable<BarTable> barTables, Bar bar, List<Reservation> currentReservations, int year, int month, int day) {
        List<Reservation> availableReservations = new ArrayList<>();
        for (BarTable bt : barTables) {
            int currentReservationStartHour = bar.getStartHour();
            int barEndTime = bar.getEndHour();
            while (currentReservationStartHour <= barEndTime - Reservation.reservationTime) {
                LocalDateTime givenHourTime = getHourTime(year, month, day, currentReservationStartHour);
                LocalDateTime endHourTime = givenHourTime.plusHours(Reservation.reservationTime);
                if (!reservationForGivenHourExists(bt, currentReservations, givenHourTime, endHourTime)) {
                    Reservation reservation = new Reservation();
                    reservation.setBar(bar);
                    reservation.setBarTable(bt);
                    reservation.setStartTime(givenHourTime);
                    reservation.setEndTime(givenHourTime.plusHours(Reservation.reservationTime));

                    availableReservations.add(reservation);
                }
                currentReservationStartHour += 1;
            }
        }
        return availableReservations;
    }

    private PremiumUser findPremiumUserByUserModel(int userModelId) {
        PremiumUser pu = premiumUserRepository.findPremiumUserIdWithUserModelId(userModelId);
        return pu;
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

    private LocalDateTime getHourTime(int year, int month, int day, int startTime) {
        LocalDate ld = LocalDate.of(year, month, day);
        LocalTime lt = LocalTime.of(startTime, 0);
        return LocalDateTime.of(ld, lt);
    }
}
