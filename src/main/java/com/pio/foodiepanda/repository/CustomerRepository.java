package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.user.email = :username")
    Customer findByEmail(String username);
}
