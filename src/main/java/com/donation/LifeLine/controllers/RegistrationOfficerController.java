package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.*;
import com.donation.LifeLine.repository.AppointmentRepository;
import com.donation.LifeLine.repository.RoleRepository;
import com.donation.LifeLine.repository.UnregisterdDonorRepository;
import com.donation.LifeLine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.donation.LifeLine.model.UnregisterdDonor;

import java.util.*;

@Controller
@RequestMapping("/registration-officer")
@PreAuthorize("hasRole('REGISTRATION_OFFICER')")
public class RegistrationOfficerController {

    @Autowired
    private UnregisterdDonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppointmentRepository appointmentRepository;



    @GetMapping("/dashboard")
    public String registrationOfficerDashboard(Model model, Authentication authentication) {
        // Get all approved donors
        List<UnregisterdDonor> approvedDonors = donorRepository
                .findByIsApprovedTrueAndIsRejectedFalseAndIsRegisteredFalse();

        List<UnregisterdDonor> registeredDonors = donorRepository
                .findByIsRegisteredTrue();

       String username = ((UserDetails) authentication.getPrincipal()).getUsername();


        User officer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));


        long totalRegisteredDonors = donorRepository.countByIsRegisteredTrue();
        long totalAppointments = appointmentRepository.count();

        List<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        model.addAttribute("officerName", officer.getUsername());
        model.addAttribute("approvedDonors", approvedDonors);
        model.addAttribute("registeredDonors", registeredDonors);
        model.addAttribute("totalRegisteredDonors", totalRegisteredDonors);
        model.addAttribute("TotalAppointments", totalAppointments);
        model.addAttribute("OfficerName"); // Placeholder, replace with actual name if available
        return "DonorRegistrationOfficer/registration-officer-dashboard";
    }

//    /* ---------- EDIT (GET) ---------- */
//    @GetMapping("/donors/edit/{id}")
//    public String editDonorForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
//        UnregisterdDonor donor = donorRepository.findById(id).orElse(null);
//        if (donor == null) {
//            ra.addFlashAttribute("error", "Donor not found");
//            return "redirect:/registration-officer/dashboard";
//        }
//        model.addAttribute("donor", donor);
//        return "DonorRegistrationOfficer/edit-donor";
//    }

    /* ---------- UPDATE (POST) ---------- */
    @PostMapping("/donors/update")
    public String updateDonor(@ModelAttribute("donor") UnregisterdDonor formDonor,
                              @RequestParam(name = "plainPassword", required = false) String plainPassword,
                              RedirectAttributes ra) {

        UnregisterdDonor donor = donorRepository.findById(formDonor.getId())
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        // NIC uniqueness check if changed
        String newNic = formDonor.getNic();
        if (!donor.getNic().equals(newNic)) {
            if (donorRepository.existsByNic(newNic) || userRepository.existsByUsername(newNic)) {
                ra.addFlashAttribute("error", "NIC already in use");
                return "redirect:/registration-officer/donors/edit/" + donor.getId();
            }
        }

        // copy editable fields
        donor.setFullName(formDonor.getFullName());
        donor.setNic(formDonor.getNic());
        donor.setAddress(formDonor.getAddress());
        donor.setBloodGroup(formDonor.getBloodGroup());
        donor.setWeight(formDonor.getWeight());
        donor.setTravelHistory(formDonor.getTravelHistory());
        donor.setDateOfBirth(formDonor.getDateOfBirth());
        donor.setHasRecentFeverOrFlu(formDonor.getHasRecentFeverOrFlu());
        donor.setIsTakingMedications(formDonor.getIsTakingMedications());
        donor.setHadRecentSurgeryOrTattoo(formDonor.getHadRecentSurgeryOrTattoo());
        donor.setHasChronicConditions(formDonor.getHasChronicConditions());
        donor.setMedicationDetails(formDonor.getMedicationDetails());
        donor.setChronicConditionDetails(formDonor.getChronicConditionDetails());

        // handle password
        if (plainPassword != null && !plainPassword.trim().isEmpty()) {
            donor.setPassword(passwordEncoder.encode(plainPassword));
        }

        donorRepository.save(donor);

        // 🔑 Sync to User table if already registered
        if (donor.getIsRegistered()) {
            User user = userRepository.findByUsername(donor.getNic())
                    .orElseThrow(() -> new RuntimeException("User not found for donor"));

            user.setUsername(donor.getNic()); // in case NIC was updated
            if (plainPassword != null && !plainPassword.trim().isEmpty()) {
                user.setPassword(donor.getPassword()); // already encoded
            }
            userRepository.save(user);
        }

        ra.addFlashAttribute("success", "Donor updated successfully");
        return "redirect:/registration-officer/dashboard";
    }

    /* ---------- DELETE DONOR (POST) ---------- */
    @PostMapping("/donors/delete/{id}")
    public String deleteDonor(@PathVariable Long id, RedirectAttributes ra) {
        // Check donor exists
        UnregisterdDonor donor = donorRepository.findById(id).orElse(null);

        if (donor == null) {
            ra.addFlashAttribute("error", "Donor not found");
            return "redirect:/registration-officer/dashboard";
        }

        // First delete donor record
        donorRepository.delete(donor);


        ra.addFlashAttribute("success", "Donor and user account deleted successfully");
        return "redirect:/registration-officer/dashboard";
    }



    // Register approved donor as system user
    @PostMapping("/register-donor")
    public String registerDonor(@RequestParam("donorId") Long donorId,
                                RedirectAttributes redirectAttrs) {

        UnregisterdDonor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        // Check if already registered
        if (userRepository.existsByUsername(donor.getNic())) {
            redirectAttrs.addFlashAttribute("error", "Donor already registered!");
            return "redirect:/registration-officer/dashboard";
        }

        User user = new User();
        user.setUsername(donor.getNic());
        user.setPassword(donor.getPassword()); // reuse existing password
        Role donorRole = roleRepository.findByName(Role.ERole.ROLE_DONOR)
                .orElseThrow(() -> new RuntimeException("ROLE_DONOR not found"));
        user.setRoles(new HashSet<>(Collections.singletonList(donorRole)));
        userRepository.save(user);
        donor.setUser(user);
        donor.setIsRegistered(true);
        donorRepository.save(donor);


        redirectAttrs.addFlashAttribute("success", "Donor registered successfully!");
        return "redirect:/registration-officer/dashboard";
    }

}