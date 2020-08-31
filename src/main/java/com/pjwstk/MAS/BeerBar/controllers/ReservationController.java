package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.models.PremiumUser;
import com.pjwstk.MAS.BeerBar.models.Reservation;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BarTableRepository;
import com.pjwstk.MAS.BeerBar.services.ReservationService;
import com.pjwstk.MAS.BeerBar.services.UserService;
import com.pjwstk.MAS.BeerBar.utils.DateUtils;
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
import java.util.List;

@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @Autowired
    BarRepository barRepository;

    @Autowired
    BarTableRepository barTableRepositoryRepository;

    @Autowired
    UserService userService;

    @GetMapping("/findSeats")
    public String findSeats(@RequestParam(name = "reservation-date") String date, @RequestParam(name = "barId") int barId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        Bar bar = barRepository.getBarById(barId);
        if (bar != null && DateUtils.isCorrectDateFormat(date)) {
            List<Reservation> availableReservations = reservationService.findAvailableReservationsByDateAndBar(date, bar);
            List<BarTable> availableSeatsForCurrentDay = reservationService.findAvailableSeatsByAvailableReservations(availableReservations);
            if (!availableReservations.isEmpty()) {
                model.addAttribute("now", LocalDate.now());
                model.addAttribute("reservationDate", date);
                model.addAttribute("availableSeats", availableSeatsForCurrentDay);
                model.addAttribute("barId", barId);
                model.addAttribute("barName", bar.getName());
                return "reservationPage";
            }
            redirectAttributes.addAttribute("barId", barId);
            redirectAttributes.addAttribute("NoReservations", "True");
            return "redirect:/bar/hasSeats";
        }
        return "redirect:/bar/bars";
    }

    @GetMapping("/availableReservationsForSeat")
    public String findAvailableReservationsForSeat(@RequestParam(name = "reservation-date") String date, @RequestParam(name = "barId") int barId,
                                                   @RequestParam(name = "seat-id") int seatId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        Bar bar = barRepository.getBarById(barId);
        if (bar != null && DateUtils.isCorrectDateFormat(date)) {
            List<Reservation> availableReservations = reservationService.findAvailableReservationsByDateAndBar(date, bar);
            List<BarTable> availableSeatsForCurrentDay = reservationService.findAvailableSeatsByAvailableReservations(availableReservations);
            availableReservations = reservationService.findAvailableReservationPerSeat(seatId, availableReservations);
            if (!availableReservations.isEmpty()) {
                model.addAttribute("now", LocalDate.now());
                model.addAttribute("reservationDate", date);
                model.addAttribute("availableSeats", availableSeatsForCurrentDay);
                model.addAttribute("barId", barId);
                model.addAttribute("barName", bar.getName());

                model.addAttribute("seatId", seatId);
                model.addAttribute("availableReservations", availableReservations);
                return "reservationPage";
            }
            redirectAttributes.addAttribute("barId", barId);
            redirectAttributes.addAttribute("NoReservations", "True");
            return "redirect:/bar/hasSeats";
        }
        return "redirect:/bar/bars";
    }

    @PostMapping("/createReservation")
    public String createReservation(@RequestParam(name = "startTime") String startTimeString, @RequestParam(name = "endTime") String endTimeString,
                                    @RequestParam(name = "tableId") int tableId, Model model, HttpSession session) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        BarTable barTable = barTableRepositoryRepository.getById(tableId);
        LocalDateTime startTime = LocalDateTime.parse(startTimeString);
        LocalDateTime endTime = LocalDateTime.parse(endTimeString);
        if (barTable != null) {
            int userModelId = (int) session.getAttribute("id");
            PremiumUser premiumUser = userService.findPremiumUserByUserModel(userModelId);
            if (premiumUser != null) {
                Reservation reservation = new Reservation();
                reservation.setUser(premiumUser);
                reservation.setStatus(Reservation.StatusType.Pending);
                reservation.setBarTable(barTable);
                reservation.setStartTime(startTime);
                reservation.setEndTime(endTime);
                reservationService.saveReservation(reservation);
                return "successfulReservation";
            }
            return "redirect:/bar/bars";
        }
        return "redirect:reservation/findSeats";


    }
}
