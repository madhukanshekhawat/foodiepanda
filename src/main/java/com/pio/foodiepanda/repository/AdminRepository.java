package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
}
