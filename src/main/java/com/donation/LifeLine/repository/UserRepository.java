package com.donation.LifeLine.repository;

import com.donation.LifeLine.model.Role;
import com.donation.LifeLine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);
        Boolean existsByUsername(String username);

        // Optional: Find all users with a given role
        List<User> findByRoles_Name(Role.ERole role);
        List<User> findByRoleIsNotNull();


    }




