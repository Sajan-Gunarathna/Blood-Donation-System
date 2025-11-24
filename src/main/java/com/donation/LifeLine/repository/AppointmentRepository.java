package com.donation.LifeLine.repository;

import com.donation.LifeLine.model.Appointment;
import com.donation.LifeLine.model.UnregisterdDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find all appointments for a specific donor
    List<Appointment> findByDonorId(Long donorId);
    long countByDonor(UnregisterdDonor donor);


}
