package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
