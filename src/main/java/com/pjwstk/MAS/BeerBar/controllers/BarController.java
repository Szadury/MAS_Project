package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BarTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/bar")
public class BarController {
    private final BarRepository barRepository;
    private final BarTableRepository barTableRepository;

    Logger logger = LoggerFactory.getLogger("IndexController");

    public BarController(BarRepository barRepository, BarTableRepository barTableRepository) {
        this.barRepository = barRepository;
        this.barTableRepository = barTableRepository;
    }
    @GetMapping("/bars")
    public String getBeers(Model model, HttpSession session) {
        List<Bar> barList = barRepository.findAll();
        model.addAttribute("bars", barList);
        return "barList";
    }

    @GetMapping("")
    public String getBeerPage(@RequestParam int id, Model model, HttpSession session) {
        Bar bar = barRepository.getBarById(id);
        logger.info("Id: " + id);
        logger.info(bar.toString());
        model.addAttribute("bar", bar);
        return "barPage";
    }

    @GetMapping("hasSeats")
    public String getReservationDatePage(@RequestParam int barId, Model model, HttpSession session) {
        Iterator<BarTable> barTableIterator = barTableRepository.getBarTablesByBarID(barId).iterator();
        logger.info("Id: " + barId);

        logger.info(String.valueOf(barTableIterator.hasNext()));
        model.addAttribute("now", LocalDate.now());
        model.addAttribute("hasSeats", barTableIterator.hasNext());
        return "reservationDate";
    }

}
