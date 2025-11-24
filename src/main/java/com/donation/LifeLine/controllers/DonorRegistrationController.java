package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.repository.UnregisterdDonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DonorRegistrationController {

    @Autowired
    private UnregisterdDonorRepository donorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show donor registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // If redirected with flash attributes (after validation), they will already be in the model.
        if (!model.containsAttribute("donor")) {
            model.addAttribute("donor", new UnregisterdDonor());
        }
        return "register"; // templates/register.html
    }

    // Handle form submit (POST from your form's th:action="@{/donor/register}")
    @PostMapping("/register")
    public String registerDonor(@ModelAttribute("donor") UnregisterdDonor donor,
                                RedirectAttributes redirectAttrs) {
        // NIC uniqueness check
        if (donorRepository.existsByNic(donor.getNic())) {
            // send back to form with an error and preserve entered data
            redirectAttrs.addFlashAttribute("error", "NIC already registered!");
            redirectAttrs.addFlashAttribute("donor", donor);
            return "redirect:/donor/register";
        }
        donor.setPassword(passwordEncoder.encode(donor.getPassword()));
        // Save to DB
        donorRepository.save(donor);

        // Send donor name to thank-you page via flash attribute
        redirectAttrs.addFlashAttribute("donorName", donor.getFullName());
        return "redirect:/thank-you";
    }

    // Thank-you page (read flash attribute donorName)
    @GetMapping("/thank-you")
    public String thankYouPage() {
        return "thank-you"; // templates/thank-you.html
    }
}
