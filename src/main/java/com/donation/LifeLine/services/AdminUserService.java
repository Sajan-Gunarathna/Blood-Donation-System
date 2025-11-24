package com.donation.LifeLine.services;

import com.donation.LifeLine.model.AdminUser;
import com.donation.LifeLine.model.DTO.UserRegistrationDTO;
import com.donation.LifeLine.model.Role;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.repository.AdminUserRepository;
import com.donation.LifeLine.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminUserService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    /** List all admin users */
    public List<AdminUser> listAll() {
        return adminUserRepository.findAll();
    }


    /** Get user by ID */
    public Optional<AdminUser> getById(Long id) {
        return adminUserRepository.findById(id);
    }

    /** Create new admin user */
    public AdminUser create(AdminUser user, String rawPassword) {
        user.setPassword(passwordEncoder.encode(rawPassword));
        return adminUserRepository.save(user);
    }

    /** Update existing admin user using DTO */
    public AdminUser updateUser(Long id, UserRegistrationDTO dto) {
        AdminUser existing = adminUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AdminUser not found with ID: " + id));

        existing.setUsername(dto.getUsername());

        if (dto.getRole() != null && !dto.getRole().isBlank()) {
            try {
                existing.setRole(Role.ERole.valueOf(dto.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role: " + dto.getRole());
            }
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return adminUserRepository.save(existing);
    }

    /** Delete user */
    public void delete(Long id) {
        adminUserRepository.deleteById(id);
    }
}
