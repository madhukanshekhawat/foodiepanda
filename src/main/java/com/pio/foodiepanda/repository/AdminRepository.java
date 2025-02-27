package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUserEmail(String email);
}
