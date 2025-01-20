package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponRepository extends JpaRepository<Coupon, Long> {

//    List<Coupon> findByRestauarnt(Long ownerId);
}
