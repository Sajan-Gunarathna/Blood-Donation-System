package com.donation.LifeLine.repository;

import com.donation.LifeLine.model.BloodUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodUnitRepository extends JpaRepository<BloodUnit, String> {

    List<BloodUnit> findByIdContainingOrBloodTypeContainingIgnoreCase(String idPart, String bloodTypePart);
}