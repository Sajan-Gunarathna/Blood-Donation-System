package com.donation.LifeLine.model.DTO;

import java.time.LocalDate;

public class DonationHistoryDTO {
    private Long id;
    private String donorName;
    private String donorNIC;
    private LocalDate donationDate;
    private String donationLocation;
    private Double hemoglobinLevel;
    private String medicalOfficer;
    private String notes;

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }

    public String getDonorNIC() { return donorNIC; }
    public void setDonorNIC(String donorNIC) { this.donorNIC = donorNIC; }

    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate donationDate) { this.donationDate = donationDate; }

    public String getDonationLocation() { return donationLocation; }
    public void setDonationLocation(String donationLocation) { this.donationLocation = donationLocation; }

    public Double getHemoglobinLevel() { return hemoglobinLevel; }
    public void setHemoglobinLevel(Double hemoglobinLevel) { this.hemoglobinLevel = hemoglobinLevel; }

    public String getMedicalOfficer() { return medicalOfficer; }
    public void setMedicalOfficer(String medicalOfficer) { this.medicalOfficer = medicalOfficer; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}