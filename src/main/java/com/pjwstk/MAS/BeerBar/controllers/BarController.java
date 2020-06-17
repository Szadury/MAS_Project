package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.Bar;
import com.pjwstk.MAS.BeerBar.models.Beer;
import com.pjwstk.MAS.BeerBar.repositories.BarRepository;
import com.pjwstk.MAS.BeerBar.repositories.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/bar")
public class BarController {
    private final BarRepository barRepository;
    Logger logger = LoggerFactory.getLogger("IndexController");

    public BarController(BarRepository barRepository) {
        this.barRepository = barRepository;
    }
    @GetMapping("/bars")
    public String getBeers(Model model, HttpSession session) {
        List<Bar> barList = barRepository.findAll();
        model.addAttribute("bars", barList);
        return "barList";
    }

}
