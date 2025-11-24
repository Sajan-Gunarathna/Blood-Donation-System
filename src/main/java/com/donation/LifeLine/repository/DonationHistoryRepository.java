package com.donation.LifeLine.repository;

import com.donation.LifeLine.model.DonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DonationHistoryRepository extends JpaRepository<DonationHistory, Long> {

    List<DonationHistory> findByDonorNIC(String donorNIC);

    @Query("SELECT d.donorNIC, d.donorName, COUNT(d) " +
            "FROM DonationHistory d " +
            "WHERE d.donationDate BETWEEN :from AND :to " +
            "GROUP BY d.donorNIC, d.donorName")
    List<Object[]> countDonationsBetween(@Param("from") LocalDate from,
                                         @Param("to") LocalDate to);
}