package com.donation.LifeLine.repository;

import com.donation.LifeLine.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    @Query("SELECT r FROM BloodRequest r WHERE LOWER(r.hospitalName) LIKE LOWER(CONCAT('%', :term, '%')) OR CAST(r.id AS string) LIKE CONCAT('%', :term, '%')")
    List<BloodRequest> searchRequests(String term);

}
