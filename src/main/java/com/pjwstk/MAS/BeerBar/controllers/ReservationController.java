package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.models.PremiumUser;
import com.pjwstk.MAS.BeerBar.models.Reservation;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BarTableRepository;
import com.pjwstk.MAS.BeerBar.repositories.PremiumUserRepository;
import com.pjwstk.MAS.BeerBar.repositories.ReservationRepository;
import org.hibernate.Hibernate;
import org.hibernate.sql.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    PremiumUserRepository premiumUserRepository;

    @GetMapping("/findSeats")
    public String findSeats(@RequestParam(name = "reservation-date") String date, @RequestParam(name = "barId") int barId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        } else {
            int year = getYearFromDate(date);
            int month = getMonthFromDate(date);
            int day = getDayFromDate(date);

            Bar bar = barRepository.getBarById(barId);
            List<BarTable> barTables = bar.getBarTables();
            List<Reservation> currentReservations = new ArrayList<>();
            for(BarTable bt: barTables){
                currentReservations.addAll(getReservationsOfTableByDate(bt, year, month, day));
            }
            List<Reservation> availableReservations = findAllAvailableReservations(barTables, bar, currentReservations, year, month, day);
            List<BarTable> availableSeatsForCurrentDay = getAvailableSeats(availableReservations);

            if (!availableReservations.isEmpty()) {
                model.addAttribute("now", LocalDate.now());
                model.addAttribute("reservationDate", date);
                model.addAttribute("availableSeats", availableSeatsForCurrentDay);
                model.addAttribute("barId", barId);
                return "reservationPage";
            } else {
                redirectAttributes.addAttribute("barId", barId);
                redirectAttributes.addAttribute("NoReservations", "True");
                return "redirect:/bar/hasSeats";
            }
        }
    }

    @GetMapping("/availableReservationsForSeat")
    public String findAvailableReservationsForSeat(@RequestParam(name = "reservation-date") String date, @RequestParam(name = "barId") int barId,
                                                   @RequestParam(name = "seat-id") int seatId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        } else {
            int year = getYearFromDate(date);
            int month = getMonthFromDate(date);
            int day = getDayFromDate(date);

            Bar bar = barRepository.getBarById(barId);
            List<BarTable> barTables = bar.getBarTables();
            List<Reservation> currentReservations = new ArrayList<>();
            for(BarTable bt: barTables){
                currentReservations.addAll(getReservationsOfTableByDate(bt, year, month, day));
            }
//            List<Reservation> currentReservations = reservationRepository.getReservationsForDateAndTable(bar, year, month, day);
            List<Reservation> availableReservations = findAllAvailableReservations(barTables, bar, currentReservations, year, month, day);
            List<BarTable> availableSeatsForCurrentDay = getAvailableSeats(availableReservations);
            availableReservations = findAvailableReservationPerSeat(seatId, bar, availableReservations, year, month, day);
            logger.info(String.valueOf(seatId));
            logger.info(String.valueOf(availableReservations));
            if (!availableReservations.isEmpty()) {
                model.addAttribute("now", LocalDate.now());
                model.addAttribute("reservationDate", date);
                model.addAttribute("availableReservations", availableReservations);
                model.addAttribute("availableSeats", availableSeatsForCurrentDay);
                model.addAttribute("seatId", seatId);
                model.addAttribute("barId", barId);
                return "reservationPage";
            } else {
                redirectAttributes.addAttribute("barId", barId);
                redirectAttributes.addAttribute("NoReservations", "True");
                return "redirect:/bar/hasSeats";
            }
        }
    }

    private List<Reservation> getReservationsOfTableByDate(BarTable bt, int year, int month,int day) {
        return bt.getReservations().stream().filter(reservation ->
                reservation.getStartTime().getYear() == year &&
                        reservation.getStartTime().getMonth().getValue() == month &&
                        reservation.getStartTime().getDayOfMonth() == day).collect(Collectors.toList());
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

    private int getYearFromDate(String date) {return Integer.parseInt(date.substring(0, 4));}

    private int getMonthFromDate(String date) {return Integer.parseInt(date.substring(5, 7));}

    private int getDayFromDate(String date) {return Integer.parseInt(date.substring(8, 10));}

    private List<BarTable> getAvailableSeats(List<Reservation> availableReservations) {
        List<BarTable> availableSeats = new ArrayList<>();
        for(Reservation reservation: availableReservations){
            BarTable bt = reservation.getBarTable();
            if(!availableSeats.contains(bt)){
                availableSeats.add(bt);
            }
        }
        return availableSeats;
    }

    private List<Reservation> findAllAvailableReservations(List<BarTable> barTables, Bar bar, List<Reservation> currentReservations, int year, int month, int day) {
        List<Reservation> availableReservations = new ArrayList<>();
        for (BarTable bt : barTables) {
            int currentReservationStartHour = bar.getStartHour();
            int barEndTime = bar.getEndHour();
            while (currentReservationStartHour <= barEndTime - Reservation.reservationTime) {
                LocalDateTime givenHourTime = getHourTime(year, month, day, currentReservationStartHour);
                LocalDateTime endHourTime = givenHourTime.plusHours(Reservation.reservationTime);
                if (!reservationForGivenHourExists(bt, currentReservations, givenHourTime, endHourTime)) {
                    Reservation reservation = new Reservation();
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

    private List<Reservation> findAvailableReservationPerSeat(int seatId, Bar bar, List<Reservation> currentReservations, int year, int month, int day){
        List<Reservation> availableReservations = new ArrayList<>();
        for(Reservation res: currentReservations){
            if(res.getBarTable().getId() == seatId){
                availableReservations.add(res);
            }
        }
        return availableReservations;
    }

    private PremiumUser findPremiumUserByUserModel(int userModelId) {
        return premiumUserRepository.findPremiumUserIdWithUserModelId(userModelId);
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
