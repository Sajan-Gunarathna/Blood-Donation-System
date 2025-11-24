package com.donation.LifeLine.repository;

import com.donation.LifeLine.model.UnregisterdDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UnregisterdDonorRepository extends JpaRepository<UnregisterdDonor, Long> {

    // You can define custom queries here if needed
    boolean existsByNic(String nic); // Example: to check if NIC is already registered

    List<UnregisterdDonor> findByIsApprovedTrueAndIsRejectedFalseAndIsRegisteredFalse();

    List<UnregisterdDonor> findByIsRegisteredTrue();

    long countByIsRegisteredTrue();


    Optional<UnregisterdDonor> findByNic(String nic);
    Optional<UnregisterdDonor> findByUserId(Long userId);
}
