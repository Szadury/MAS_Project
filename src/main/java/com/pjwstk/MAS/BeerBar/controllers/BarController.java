package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BarTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    BarRepository barRepository;

    @Autowired
    BarTableRepository barTableRepository;

    @GetMapping("/bars")
    public String getBeers(Model model, HttpSession session) {
        List<Bar> barList = barRepository.findAll();
        if(session.getAttribute("id") == null){
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        else{
            model.addAttribute("bars", barList);
            return "barList";
        }
    }

    @GetMapping("")
    public String getBeerPage(@RequestParam int id, Model model, HttpSession session) {
        if(session.getAttribute("id") == null){
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        else {
            Bar bar = barRepository.getBarById(id);
            model.addAttribute("bar", bar);
            return "barPage";
        }
    }

    @GetMapping("hasSeats")
    public String getReservationDatePage(@RequestParam int barId, Model model, HttpSession session) {
        if(session.getAttribute("id") == null){
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        else {
            Iterator<BarTable> barTableIterator = barTableRepository.getBarTablesByBarID(barId).iterator();
            model.addAttribute("now", LocalDate.now());
            model.addAttribute("hasSeats", barTableIterator.hasNext());
            model.addAttribute("barId", barId);
            //Jeżeli użytkownik zawiera w sb użytkownika premium
            return "reservationDate";
        }
    }
}
