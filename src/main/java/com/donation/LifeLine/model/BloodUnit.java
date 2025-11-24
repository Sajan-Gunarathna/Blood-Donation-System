package com.donation.LifeLine.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_units")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodUnit {

    @Id
    @Column(length = 10)
    private String id;

    @Column(name = "blood_type", nullable = false, length = 10)
    private String bloodType;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "audit_log", columnDefinition = "json")
    private String auditLog;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}