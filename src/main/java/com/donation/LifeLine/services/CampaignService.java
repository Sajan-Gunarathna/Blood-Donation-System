package com.donation.LifeLine.services;

import com.donation.LifeLine.model.Campaign;
import com.donation.LifeLine.repository.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<Campaign> listAll() { return campaignRepository.findAll(); }
    public Optional<Campaign> getById(Long id) { return campaignRepository.findById(id); }
    public Campaign create(Campaign c) { return campaignRepository.save(c); }
    public Campaign update(Long id, Campaign updated) {
        Campaign existing = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        existing.setActive(updated.isActive());
        return campaignRepository.save(existing);
    }
    public void delete(Long id) { campaignRepository.deleteById(id); }
}

