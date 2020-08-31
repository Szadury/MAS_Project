package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.BarTable;
import com.pjwstk.MAS.BeerBar.models.PremiumUser;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BarTableRepository;
import com.pjwstk.MAS.BeerBar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "/bar")
public class BarController {

    @Autowired
    BarRepository barRepository;

    @Autowired
    BarTableRepository barTableRepository;

    @Autowired
    UserService userService;

    @GetMapping("/bars")
    public String getBars(Model model, HttpSession session) {
        List<Bar> barList = barRepository.findAll();
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        model.addAttribute("bars", barList);
        return "barList";

    }

    @GetMapping("")
    public String getBarPage(@RequestParam int id, @RequestParam(required = false) String noSeats, Model model, HttpSession session) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        Bar bar = barRepository.getBarById(id);
        if (bar != null) {
            int userModelId = (int) session.getAttribute("id");
            PremiumUser premiumUser = userService.findPremiumUserByUserModel(userModelId);
            model.addAttribute("bar", bar);
            if (premiumUser != null) {
                model.addAttribute("isPremium", "true");
            }
            if (noSeats != null) {
                model.addAttribute("noSeats", "true");
            }
            return "barPage";
        }
        return "redirect:/bar/bars";

    }

    @GetMapping("hasSeats")
    public String getReservationDatePage(@RequestParam int barId, @RequestParam(required = false, name = "NoReservations") String noReservations, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("id") == null) {
            model.addAttribute("loginFirst", "not logged in");
            return "login";
        }
        Bar bar = barRepository.getBarById(barId);
        if (bar != null) {
            List<BarTable> barTableList = bar.getBarTables();
            if (!barTableList.isEmpty()) {
                model.addAttribute("now", LocalDate.now());
                model.addAttribute("hasSeats", !barTableList.isEmpty());
                model.addAttribute("barId", barId);
                model.addAttribute("barName", bar.getName());
                if (noReservations != null) {
                    model.addAttribute("noReservations", "True");
                }
                return "reservationPage";
            }
            redirectAttributes.addAttribute("noSeats", "true");
            redirectAttributes.addAttribute("id", barId);
            return "redirect:/bar";
        }
        return "redirect:/bar/bars";
    }
}
