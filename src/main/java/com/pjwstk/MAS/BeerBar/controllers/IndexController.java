package com.pjwstk.MAS.BeerBar.controllers;

import com.pjwstk.MAS.BeerBar.models.UserModel;
import com.pjwstk.MAS.BeerBar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String authLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {
        Iterator<UserModel> userIterable = userService.findUserWithPassword(username, password).iterator();
        if (userIterable.hasNext()) {
            UserModel userModel = userIterable.next();
            session.setAttribute("username", username);
            session.setAttribute("id", userModel.getId());
            return "redirect:bar/bars";
        }
        model.addAttribute("loginError", "User not found");
        return "login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("id");
        return "redirect:";
    }
}
