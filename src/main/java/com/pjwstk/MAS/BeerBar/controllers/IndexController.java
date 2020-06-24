package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.UserModel;
import com.pjwstk.MAS.BeerBar.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Controller
public class IndexController {

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String get() {
        return "login";
    }

    @PostMapping("/login")
    public String authLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {
        Iterator<UserModel> userIterable = userRepository.findUserWithPassword(username, password).iterator();
        if (userIterable.hasNext()) {
            UserModel userModel = userIterable.next();
            logger.info(userModel.toString());
            session.setAttribute("username", username);
            session.setAttribute("id", userModel.getId());
            return "redirect:bar/bars";
        } else {
            model.addAttribute("loginError", "User not found");
            return "login";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("id");
        return "redirect:login";
    }
}
