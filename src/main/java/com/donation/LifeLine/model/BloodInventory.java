package com.donation.LifeLine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;

@Entity
@Table(name = "blood_inventory", uniqueConstraints = {
        @UniqueConstraint(name = "uk_blood_inventory_type", columnNames = {"bloodType"})
})
public class BloodInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood type")
    @Column(nullable = false, length = 3)
    private String bloodType;

    @Min(0)
    @Column(nullable = false)
    private int unitsAvailable = 0;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public int getUnitsAvailable() { return unitsAvailable; }
    public void setUnitsAvailable(int unitsAvailable) { this.unitsAvailable = unitsAvailable; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}


