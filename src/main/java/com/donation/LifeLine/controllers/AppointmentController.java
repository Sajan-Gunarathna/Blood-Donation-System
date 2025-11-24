package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.Appointment;
import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.repository.AppointmentRepository;
import com.donation.LifeLine.repository.UnregisterdDonorRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final UnregisterdDonorRepository donorRepository;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 UnregisterdDonorRepository donorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.donorRepository = donorRepository;
    }



    // Save appointment
    @PostMapping("/save")
    public String saveAppointment(@RequestParam Long donorId,
                                  @RequestParam("appointmentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                  @RequestParam String timeSlot,
                                  @RequestParam String location,
                                  @RequestParam(required = false) String notes){

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past.");
        }


        UnregisterdDonor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        Appointment appointment = new Appointment();
        appointment.setDonor(donor);
        appointment.setAppointmentDate(date);
        appointment.setTimeSlot(timeSlot);
        appointment.setLocation(location);
        appointment.setNotes(notes);

        appointmentRepository.save(appointment);
        return "redirect:/registration-officer/dashboard";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        model.addAttribute("appointment", appointment);
        model.addAttribute("registeredDonors", donorRepository.findAll());
        return "DonorRegistrationOfficer/edit-appointment"; // a new template
    }

    // Update appointment
    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable Long id,
                                    @RequestParam Long donorId,
                                    @RequestParam("appointmentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                    @RequestParam String timeSlot,
                                    @RequestParam String location,
                                    @RequestParam(required = false) String notes) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        UnregisterdDonor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        appointment.setDonor(donor);
        appointment.setAppointmentDate(date);
        appointment.setTimeSlot(timeSlot);
        appointment.setLocation(location);
        appointment.setNotes(notes);


        appointmentRepository.save(appointment);
        return "redirect:/registration-officer/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return "redirect:/registration-officer/dashboard";
    }


    @GetMapping("/donor/{id}/dashboard")
    public String donorDashboard(@PathVariable Long id, Model model) {
        UnregisterdDonor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        List<Appointment> appointments = appointmentRepository.findByDonorId(id);

        model.addAttribute("donor", donor);
        model.addAttribute("appointments", appointments);

        return "Donor/donor-dashboard"; // your Thymeleaf template
    }

}
