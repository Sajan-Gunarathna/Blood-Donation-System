package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.Campaign;
import com.donation.LifeLine.services.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/campaigns")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        return campaignService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Campaign createCampaign(@RequestBody Campaign campaign) {
        return campaignService.create(campaign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable Long id,
            @RequestBody Campaign campaign) {
        try {
            Campaign updated = campaignService.update(id, campaign);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable Long id) {
        campaignService.delete(id);
    }
}