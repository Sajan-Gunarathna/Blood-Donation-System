package com.donation.LifeLine.services;

import com.donation.LifeLine.model.BloodInventory;
import com.donation.LifeLine.repository.BloodInventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BloodInventoryService {
    private final BloodInventoryRepository bloodInventoryRepository;

    public BloodInventoryService(BloodInventoryRepository bloodInventoryRepository) {
        this.bloodInventoryRepository = bloodInventoryRepository;
    }

    public List<BloodInventory> listAll() { return bloodInventoryRepository.findAll(); }
    public Optional<BloodInventory> getById(Long id) { return bloodInventoryRepository.findById(id); }
    public BloodInventory create(BloodInventory b) { return bloodInventoryRepository.save(b); }
    public BloodInventory update(Long id, BloodInventory updated) {
        BloodInventory existing = bloodInventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BloodInventory not found"));
        existing.setBloodType(updated.getBloodType());
        existing.setUnitsAvailable(updated.getUnitsAvailable());
        return bloodInventoryRepository.save(existing);
    }
    public void delete(Long id) { bloodInventoryRepository.deleteById(id); }
}


