package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Cart;
import com.pio.foodiepanda.model.Customer;
import com.pio.foodiepanda.model.MenuItem;
import com.pio.foodiepanda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByCustomer(Customer customer);

    Optional<Cart> findByMenuItemIdAndCustomer(Customer customer , MenuItem menuItem);
}
