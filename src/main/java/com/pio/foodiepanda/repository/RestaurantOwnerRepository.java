package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Long> {
    List<RestaurantOwner> findByIsApprovedFalseAndRejectedFalse();

    List<RestaurantOwner> findByIsApprovedTrue();

    @Modifying
    @Query("UPDATE RestaurantOwner o SET o.rejected = true WHERE o.ownerID = :ownerId")
    void softDeleteOwner(@Param("ownerId") Long ownerId);

    RestaurantOwner findByUserEmail(String email);

//    RestaurantOwner findByEmail(String email);
}
