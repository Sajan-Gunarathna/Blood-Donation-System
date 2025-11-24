package com.donation.LifeLine.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/hospital-coordinator")
@PreAuthorize("hasRole('HOSPITAL_COORDINATOR')")
public class HospitalCoordinatorController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Hospital_Coordinator/hospital-coordinator-dashboard"; // This should match your HTML template name
    }
}