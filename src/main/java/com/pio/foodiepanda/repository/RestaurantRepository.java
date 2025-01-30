package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Restaurant;
import com.pio.foodiepanda.model.RestaurantOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByRestaurantOwner(RestaurantOwner owner);

    @Query("SELECT r from Restaurant r  WHERE r.restaurantOwner.user.email = :email")
    List<Restaurant> findByRestaurantOwnerUsername(@Param("email") String ownerUsername);

    @Query("SELECT r from Restaurant r WHERE r.restaurantOwner.user.email = :email")
    Optional<Restaurant> findByOwnerEmail(@Param("email") String username);

    @Query("SELECT r FROM Restaurant r WHERE r.restaurantOwner.isApproved = true")
    List<Restaurant> findAllByOwnerApproved();

    @Query("SELECT r FROM Restaurant r WHERE r.restaurantOwner.isApproved = true")
    Page<Restaurant> findAllRestaurantByOwnerApproved(Pageable pageable);
}
