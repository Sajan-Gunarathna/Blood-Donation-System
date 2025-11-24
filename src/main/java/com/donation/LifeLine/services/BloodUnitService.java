package com.donation.LifeLine.services;

import com.donation.LifeLine.model.BloodUnit;
import com.donation.LifeLine.repository.BloodUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodUnitService {

    private final BloodUnitRepository bloodUnitRepository;

    @Autowired
    public BloodUnitService(BloodUnitRepository bloodUnitRepository) {
        this.bloodUnitRepository = bloodUnitRepository;
    }

    public List<BloodUnit> listAll() {
        return bloodUnitRepository.findAll();
    }

    public Optional<BloodUnit> getById(String id) {
        return bloodUnitRepository.findById(id);
    }

    public BloodUnit create(BloodUnit bloodUnit) {
        return bloodUnitRepository.save(bloodUnit);
    }

    public BloodUnit update(String id, BloodUnit bloodUnit) {
        if (!bloodUnitRepository.existsById(id)) {
            throw new IllegalArgumentException("Blood unit not found with id: " + id);
        }
        bloodUnit.setId(id);
        return bloodUnitRepository.save(bloodUnit);
    }

    public void delete(String id) {
        bloodUnitRepository.deleteById(id);
    }

    public List<BloodUnit> searchUnits(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return bloodUnitRepository.findAll();
        }
        String term = searchTerm.toLowerCase();
        return bloodUnitRepository.findByIdContainingOrBloodTypeContainingIgnoreCase(term, term);
    }

    // Backwards-compatible methods used by BloodUnitController
    public Optional<BloodUnit> findUnitById(String id) {
        return getById(id);
    }

    public BloodUnit saveUnit(BloodUnit unit) {
        return bloodUnitRepository.save(unit);
    }

    public void deleteUnit(String id) {
        delete(id);
    }
}