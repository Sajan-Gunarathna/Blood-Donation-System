package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.BloodUnit;
import com.donation.LifeLine.model.DTO.UpdateBloodUnitDTO;
import com.donation.LifeLine.services.BloodUnitService; // <-- Corrected import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blood-units")
@CrossOrigin(origins = "*")
public class BloodUnitController {

    private final BloodUnitService bloodUnitService;

    @Autowired
    public BloodUnitController(BloodUnitService bloodUnitService) {
        this.bloodUnitService = bloodUnitService;
    }

    @GetMapping
    public List<BloodUnit> getAllBloodUnits(@RequestParam(required = false) String search) {
        return bloodUnitService.searchUnits(search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodUnit> getBloodUnitById(@PathVariable String id) {
        return bloodUnitService.findUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodUnit createBloodUnit(@RequestBody BloodUnit unit) {
        return bloodUnitService.saveUnit(unit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodUnit> updateBloodUnit(@PathVariable String id, @RequestBody UpdateBloodUnitDTO payload) {
        return bloodUnitService.findUnitById(id)
                .map(existingUnit -> {
                    if (payload.getQuantity() != null) existingUnit.setQuantity(payload.getQuantity());
                    if (payload.getStatus() != null) existingUnit.setStatus(payload.getStatus());
                    if (payload.getBloodType() != null) existingUnit.setBloodType(payload.getBloodType());
                    if (payload.getDonationDate() != null) existingUnit.setDonationDate(payload.getDonationDate());
                    if (payload.getExpiryDate() != null) existingUnit.setExpiryDate(payload.getExpiryDate());
                    return ResponseEntity.ok(bloodUnitService.saveUnit(existingUnit));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBloodUnit(@PathVariable String id) {
        bloodUnitService.deleteUnit(id);
    }
}