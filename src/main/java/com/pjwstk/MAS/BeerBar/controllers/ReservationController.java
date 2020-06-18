package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Beer;
import com.pjwstk.MAS.BeerBar.repositories.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;

    Logger logger = LoggerFactory.getLogger("ReservationController");
    @PostMapping("/findSeats")
    public String findSeats(@RequestParam(name = "reservation-date") String date, @RequestParam(name = "barId") int barId){
        logger.info(date);
        logger.info(String.valueOf(barId));

        return "login";
    }

}
