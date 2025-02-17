package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Customer;
import com.pio.foodiepanda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.user.email = :username")
    Customer findByEmail(String username);

    @Query("SELECT c FROM Customer c WHERE c.user.id = :userId")
    Customer findByUser(@Param("userId") Long userId);
}
