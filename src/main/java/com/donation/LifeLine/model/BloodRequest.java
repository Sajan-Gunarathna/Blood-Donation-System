package com.donation.LifeLine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

@Entity
@Table(name = "blood_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hospital name is required")
    private String hospitalName;

    @NotBlank(message = "Requester name is required")
    private String requesterName;

    @NotBlank(message = "Contact is required")
    private String contact;

    @NotBlank(message = "Blood type is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood type")
    private String bloodType;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotBlank(message = "Urgency level is required")
    private String urgency;

    private String patientDetails;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime neededBy;

    @NotNull(message = "Status is required")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime requestDate;
}