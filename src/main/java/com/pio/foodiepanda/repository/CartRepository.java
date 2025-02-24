package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Cart;
import com.pio.foodiepanda.model.Customer;
import com.pio.foodiepanda.model.MenuItem;
import com.pio.foodiepanda.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByCustomer(Customer customer);

    Optional<Cart> findByMenuItemIdAndCustomer(Customer customer , MenuItem menuItem);

    @Query("SELECT c FROM Cart c WHERE c.cartId = :cartId AND c.customer.id = :customerId")
    Cart findByCartIdAndCustomerId(@Param("cartId") Long cartId, @Param("customerId") Long customerId);

    @Query("SELECT c FROM Cart c WHERE c.customer.id = :customerId")
    List<Cart> findByCustomerId(@Param("customerId") Long customerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.customer.id = :customerId AND c.menuItemId = :menuItemId")
    void deleteByCustomerIdAndMenuItemId(Long customerId, Long menuItemId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.customer.id = :customerID")
    void deleteByCustomerId(Long customerID);
}
