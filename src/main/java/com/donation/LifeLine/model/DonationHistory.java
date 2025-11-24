package com.donation.LifeLine.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Entity
@Table(name = "donation_history")
public class DonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "donor_name", nullable = false)
    private String donorName;

    @Column(name = "donor_nic", nullable = false)
    private String donorNIC;

    @Column(name = "donation_date")
    @JsonFormat(pattern = "yyyy-MM-dd")   // ✅ enforce correct date format
    private LocalDate donationDate;

    @Column(name = "donation_location")
    private String donationLocation;

    @Column(name = "hemoglobin_level")
    private Double hemoglobinLevel;

    @Column(name = "medical_officer")
    private String medicalOfficer;

    @Column(length = 2000)
    private String notes;

    // --- getters and setters ---
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