package com.pio.foodiepanda;

import com.pio.foodiepanda.enums.UserRole;
import com.pio.foodiepanda.model.Admin;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.AdminRepository;
import com.pio.foodiepanda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        if (userRepository.findByEmail("mani@gmail.com")==null) {
            // Create an admin user with their login details
            Admin admin = new Admin();
            admin.setFirstName("Madhu");
            admin.setLastName("Shekhawat");

            // Save the admin entity first
            adminRepository.save(admin);

            User loginInfo = new User();
            loginInfo.setEmail("mani@gmail.com");
            loginInfo.setPassword(passwordEncoder.encode("ab"));
            loginInfo.setRole(UserRole.ADMIN);
            loginInfo.setAdmin(admin);

            // Save the user entity
            userRepository.save(loginInfo);
        }
    }
}
