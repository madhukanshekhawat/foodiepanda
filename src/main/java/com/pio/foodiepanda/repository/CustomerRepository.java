package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Customer;
import com.pio.foodiepanda.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.user.email = :username")
    Customer findByEmail(String username);

    @Query("SELECT c FROM Customer c WHERE c.user.id = :userId")
    Customer findByUser(@Param("userId") Long userId);

    @Query("SELECT c from Customer c WHERE c.user.email = :email")
    Optional<Customer> findByCustomerEmail(@Param("email") String username);
}
