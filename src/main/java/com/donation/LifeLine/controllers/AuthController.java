package com.donation.LifeLine.controllers;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("user/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }



    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
