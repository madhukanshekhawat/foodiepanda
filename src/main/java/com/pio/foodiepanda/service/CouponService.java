package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.CouponDTO;
import com.pio.foodiepanda.model.Coupon;

import java.util.List;

public interface CouponService {

    Coupon createCoupon(CouponDTO couponDTO);

//    List<CouponDTO> getCouponByRestaurantId(Long ownerId);

}
