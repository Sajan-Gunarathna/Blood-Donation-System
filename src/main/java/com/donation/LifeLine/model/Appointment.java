package com.donation.LifeLine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to donor
    @ManyToOne
    @JoinColumn(name = "DonorId")
    private UnregisterdDonor donor;

    private LocalDate appointmentDate;
    private String timeSlot;
    private String location;

    @Column(length = 500)
    private String notes;

    private String status = "Scheduled"; // default value

}
