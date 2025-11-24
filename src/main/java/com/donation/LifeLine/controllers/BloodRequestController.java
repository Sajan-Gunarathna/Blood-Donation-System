package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.BloodRequest;
import com.donation.LifeLine.services.BloodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*") // Add this to handle CORS
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    @PostMapping
    public ResponseEntity<?> createRequest(@Valid @RequestBody BloodRequest request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                result.getFieldErrors().forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errors);
            }

            BloodRequest savedRequest = bloodRequestService.createRequest(request);
            return ResponseEntity.ok(savedRequest);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating request: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable Long id, @Valid @RequestBody BloodRequest request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                result.getFieldErrors().forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errors);
            }

            BloodRequest updatedRequest = bloodRequestService.updateRequest(id, request);
            return ResponseEntity.ok(updatedRequest);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating request: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BloodRequest>> getAllRequests() {
        return ResponseEntity.ok(bloodRequestService.getAllRequests());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id) {
        try {
            bloodRequestService.deleteRequest(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting request: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BloodRequest>> searchRequests(@RequestParam String term) {
        return ResponseEntity.ok(bloodRequestService.searchRequests(term));
    }
}