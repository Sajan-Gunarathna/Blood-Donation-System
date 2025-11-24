package com.donation.LifeLine.config;

import com.donation.LifeLine.model.AdminUser;
import com.donation.LifeLine.model.BloodInventory;
import com.donation.LifeLine.model.BloodUnit;
import com.donation.LifeLine.model.Role;
import com.donation.LifeLine.repository.AdminUserRepository;
import com.donation.LifeLine.repository.BloodInventoryRepository;
import com.donation.LifeLine.repository.BloodUnitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(AdminUserRepository users, BloodInventoryRepository inventory, BloodUnitRepository bloodUnitRepository) {
        return args -> {
            // Your existing admin user seeding logic
            if (users.count() == 0) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                AdminUser admin = new AdminUser();
                admin.setUsername("admin");
                admin.setRole(Role.ERole.ROLE_ADMIN);
                admin.setPassword(encoder.encode("12345"));
                users.save(admin);
            }

            // Your existing blood inventory seeding logic
            String[] types = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
            for (String t : types) {
                inventory.findByBloodType(t).orElseGet(() -> {
                    BloodInventory b = new BloodInventory();
                    b.setBloodType(t);
                    b.setUnitsAvailable(0);
                    return inventory.save(b);
                });
            }

            // New logic to seed the individual blood units
            if (bloodUnitRepository.count() == 0) {
                List<BloodUnit> units = List.of(
                        new BloodUnit("BU001", "A+", 2, LocalDate.parse("2024-01-15"), LocalDate.parse("2024-07-15"), "Available", "[{\"action\": \"Created\"}]", null, null),
                        new BloodUnit("BU002", "A-", 1, LocalDate.parse("2024-01-10"), LocalDate.parse("2024-07-10"), "Used", "[{\"action\": \"Used\"}]", null, null),
                        new BloodUnit("BU003", "B+", 3, LocalDate.parse("2024-01-20"), LocalDate.parse("2024-07-20"), "Available", "[{\"action\": \"Created\"}]", null, null),
                        new BloodUnit("BU004", "B-", 1, LocalDate.parse("2024-01-05"), LocalDate.parse("2024-07-05"), "Expired", "[{\"action\": \"Expired\"}]", null, null),
                        new BloodUnit("BU005", "AB+", 2, LocalDate.parse("2024-01-25"), LocalDate.parse("2024-07-25"), "Available", "[{\"action\": \"Created\"}]", null, null),
                        new BloodUnit("BU006", "AB-", 1, LocalDate.parse("2024-01-30"), LocalDate.parse("2024-07-30"), "Removed", "[{\"action\": \"Removed\"}]", null, null),
                        new BloodUnit("BU007", "O+", 45, LocalDate.parse("2024-02-01"), LocalDate.parse("2024-08-01"), "Available", "[{\"action\": \"Created\"}]", null, null),
                        new BloodUnit("BU008", "O-", 4, LocalDate.parse("2024-02-05"), LocalDate.parse("2024-08-05"), "Available", "[{\"action\": \"Created\"}]", null, null)
                );
                bloodUnitRepository.saveAll(units);
            }
        };
    }
}