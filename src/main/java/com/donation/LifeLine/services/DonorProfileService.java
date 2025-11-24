package com.donation.LifeLine.services;

import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.repository.UnregisterdDonorRepository;
import com.donation.LifeLine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DonorProfileService {

    @Autowired
    private UnregisterdDonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Fetch donor by username
    public UnregisterdDonor getDonorByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return donorRepository.findByNic(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Donor not found"));
    }

    // Update donor profile
    public UnregisterdDonor updateDonor(UnregisterdDonor donor) {
        return donorRepository.save(donor);
    }

    // Update password
    public void updatePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    // Fetch donor by userId
    public UnregisterdDonor getDonorByUserId(Long userId) {
        return donorRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Donor not found for userId: " + userId));
    }


}

