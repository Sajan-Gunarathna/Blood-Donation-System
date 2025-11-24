package com.donation.LifeLine.repository;

import com.donation.LifeLine.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}

