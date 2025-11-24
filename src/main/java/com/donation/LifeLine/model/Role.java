package com.donation.LifeLine.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    // Enum for roles
    public enum ERole {
        ROLE_DONOR,
        ROLE_ADMIN,
        ROLE_MEDICAL_ADVISER,
        ROLE_REGISTRATION_OFFICER,
        ROLE_IT_SUPPORT_ASSISTANT,
        ROLE_HOSPITAL_COORDINATOR,
        ROLE_PUBLIC_AWARENESS_MANAGER,
        ROLE_SYSTEM_ADMINISTRATOR,
        ROLE_FIELD_OPERATION_SUPERVISOR

    }


}
