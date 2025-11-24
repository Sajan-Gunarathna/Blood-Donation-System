package com.donation.LifeLine.services;

import com.donation.LifeLine.model.User;
import com.donation.LifeLine.model.Role;
import com.donation.LifeLine.model.Role.ERole;
import com.donation.LifeLine.repository.UserRepository;
import com.donation.LifeLine.repository.RoleRepository;
import com.donation.LifeLine.model.DTO.UserRegistrationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDTO dto) throws Exception {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new Exception("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Assign role using enum (default ROLE_DONOR)
        Set<Role> roles = new HashSet<>();
        ERole roleEnum = dto.getRole() != null ? ERole.valueOf(dto.getRole()) : ERole.ROLE_DONOR;
        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new Exception("Role not found: " + roleEnum));
        roles.add(role);
        user.setRoles(roles);
        user.setRole(roleEnum.name());
        return userRepository.save(user);
    }
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> listUsersWithRoles() {
        return userRepository.findByRoleIsNotNull();
    }




    public void updateUser(Long id, String username, String password, String roleStr) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
        user.setUsername(username);
        if (password != null && !password.isBlank())
            user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByName(ERole.valueOf(roleStr))
                .orElseThrow(() -> new Exception("Role not found: " + roleStr));
        user.setRoles(Set.of(role));

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

