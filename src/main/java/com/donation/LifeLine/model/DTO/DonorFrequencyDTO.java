package com.donation.LifeLine.model.DTO;

public class DonorFrequencyDTO {
    private String donorNIC;
    private String donorName;
    private Long donationCount;

    public DonorFrequencyDTO() {}

    public DonorFrequencyDTO(String donorNIC, String donorName, Long donationCount) {
        this.donorNIC = donorNIC;
        this.donorName = donorName;
        this.donationCount = donationCount;
    }

    public String getDonorNIC() { return donorNIC; }
    public void setDonorNIC(String donorNIC) { this.donorNIC = donorNIC; }

    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }

    public Long getDonationCount() { return donationCount; }
    public void setDonationCount(Long donationCount) { this.donationCount = donationCount; }
}
