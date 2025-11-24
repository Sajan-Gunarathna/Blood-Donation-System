package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.BloodInventory;
import com.donation.LifeLine.services.BloodInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inventory")
@CrossOrigin(origins = "*")
public class InventoryApiController {

    private final BloodInventoryService bloodInventoryService;

    public InventoryApiController(BloodInventoryService bloodInventoryService) {
        this.bloodInventoryService = bloodInventoryService;
    }

    @GetMapping
    public List<BloodInventory> getAll() {
        return bloodInventoryService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodInventory> getById(@PathVariable Long id) {
        return bloodInventoryService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BloodInventory create(@RequestBody BloodInventory inventory) {
        return bloodInventoryService.create(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodInventory> update(@PathVariable Long id,
                                                 @RequestBody BloodInventory inventory) {
        try {
            return ResponseEntity.ok(bloodInventoryService.update(id, inventory));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bloodInventoryService.delete(id);
    }
}





