package com.pio.foodiepanda.service;

import com.pio.foodiepanda.constants.CouponStatus;
import com.pio.foodiepanda.dto.CouponDTO;
import com.pio.foodiepanda.model.Coupon;

import java.util.List;

public interface CouponService {

    Coupon createCoupon(CouponDTO couponDTO);

    List<CouponDTO> getCouponsByOwner(String ownerUsername);

    void updateCouponStatus(Long id, String ownerUsername, CouponStatus status);
}
