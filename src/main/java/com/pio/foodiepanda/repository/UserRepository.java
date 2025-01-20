package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
