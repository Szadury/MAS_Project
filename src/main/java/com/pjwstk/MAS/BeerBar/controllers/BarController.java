package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BarTableRepository;
import com.pjwstk.MAS.BeerBar.repositories.PremiumUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    PremiumUserRepository premiumUserRepository;

    @GetMapping("/bars")
    public String getBars(Model model, HttpSession session) {
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
    public String getBarPage(@RequestParam int id, @RequestParam(required = false) String noSeats, Model model, HttpSession session) {
        if(session.getAttribute("id") == null){
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        else {
            Bar bar = barRepository.getBarById(id);
            int userModelId = (int) session.getAttribute("id");
            //If current user is premium, than he will see reservation button
            int premiumUserId = findPremiumUserByUserModel(userModelId);
            if(premiumUserId != -1){
                model.addAttribute("isPremium", "true");
            }
            model.addAttribute("bar", bar);
            if(noSeats != null){
                model.addAttribute("noSeats", "true");
            }
            return "barPage";
        }
    }

    @GetMapping("hasSeats")
    public String getReservationDatePage(@RequestParam int barId, @RequestParam(required = false, name = "NoReservations") String noReservations, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if(session.getAttribute("id") == null){
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        else {
            Iterator<BarTable> barTableIterator = barTableRepository.getBarTablesByBarID(barId).iterator();
            if(barTableIterator.hasNext()) {
                model.addAttribute("now", LocalDate.now());
                model.addAttribute("hasSeats", barTableIterator.hasNext());
                model.addAttribute("barId", barId);
                if(noReservations != null){
                    model.addAttribute("noReservations", "True");
                }
                return "reservationDate";
            }
            else {
                redirectAttributes.addAttribute("noSeats", "true");
                redirectAttributes.addAttribute("id", barId);
                return "redirect:/bar";
            }
        }
    }

    private int findPremiumUserByUserModel(int userModelId) {
        Integer id = premiumUserRepository.findPremiumUserIdWithUserModelId(userModelId);
        return id != null ? id : -1;
    }
}
