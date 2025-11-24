package com.donation.LifeLine.model;

import jakarta.persistence.*; // or use javax.persistence.* depending on your setup
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "unregistered_donor")  // Optional: customize table name
@Getter
@Setter
public class UnregisterdDonor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment (MySQL)
    private Long id;

    // Donor Info
    private String fullName;
    private String nic;
    private String address;
    private String bloodGroup;
    private String password;
    private int weight;
    private String travelHistory;
    private String dateOfBirth;

    // Health Screening Questions
    private Boolean hasRecentFeverOrFlu;
    private Boolean isTakingMedications;
    private Boolean hadRecentSurgeryOrTattoo;
    private Boolean hasChronicConditions;

    // Optional Details
    private String medicationDetails;
    private String chronicConditionDetails;

    // Approval Status
    @Column(nullable = false)
    private Boolean isApproved = false;

    @Column(nullable = false)
    private Boolean isRejected = false;

    @Column(nullable = false)
    private Boolean isRegistered = false;

    // UnregisterdDonor.java
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;
}
