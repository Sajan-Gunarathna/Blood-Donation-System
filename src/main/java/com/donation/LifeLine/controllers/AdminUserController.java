package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.AdminUser;
import com.donation.LifeLine.model.DTO.UserRegistrationDTO;
import com.donation.LifeLine.model.DonationHistory;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.services.AdminUserService;
import com.donation.LifeLine.services.DonationHistoryService;
import com.donation.LifeLine.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;
    private final DonationHistoryService donationHistoryService;
    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(UserService userService, DonationHistoryService donationHistoryService, AdminUserService adminUserService) {
        this.userService = userService;
        this.donationHistoryService = donationHistoryService;
        this.adminUserService = adminUserService;
    }
    // Thymeleaf dashboard page

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin/admin-dashboard"; // Thymeleaf template
    }
     // Create a new user in USERS table (returns plain text only)

    @PostMapping("/users")
    @ResponseBody
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest req) {
        try {
            // Prepare DTO
            UserRegistrationDTO dto = new UserRegistrationDTO();
            dto.setUsername(req.username);
            dto.setPassword(req.password);

            // Automatically add "ROLE_" prefix if missing
            String roleName = req.role.startsWith("ROLE_") ? req.role : "ROLE_" + req.role;
            dto.setRole(roleName);

            // Save user
            User user = userService.registerUser(dto);

            // ✅ Return JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User created successfully");
            response.put("userId", user.getId());
            response.put("role", roleName);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error creating user: " + (e.getMessage() != null ? e.getMessage() : "Unknown error"));
            return ResponseEntity.badRequest().body(error);
        }
    }
   //load users
    @GetMapping("/users")
    @ResponseBody
    public List<User> listUsers() {
        return userService.listUsersWithRoles();
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    //update user
    @PutMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRegistrationDTO req) {
        try {
            AdminUser updatedUser = adminUserService.updateUser(id, req);
            return ResponseEntity.ok(updatedUser); // Return full updated user object
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Error updating user: " + e.getClass().getSimpleName() +
                            (e.getMessage() != null ? " - " + e.getMessage() : ""));
        }
    }


    // Delete user
    @DeleteMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }


    public static class CreateUserRequest {
        public String username;
        public String password;
        public String role;
    }
}
