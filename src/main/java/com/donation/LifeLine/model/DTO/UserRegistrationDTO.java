package com.donation.LifeLine.model.DTO;

public class UserRegistrationDTO {
    private String username;
    private String password;

    // Optional role, should match enum names (e.g., "ROLE_DONOR")
    private String role;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
