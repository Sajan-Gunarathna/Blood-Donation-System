package com.donation.LifeLine.controllers;



import com.donation.LifeLine.model.DTO.UserRegistrationDTO;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRegistrationDTO dto) {
        try {
            User user = userService.registerUser(dto);
            return "User registered successfully with ID: " + user.getId();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
