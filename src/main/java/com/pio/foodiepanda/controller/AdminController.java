package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CouponDTO;
import com.pio.foodiepanda.dto.RestaurantOwnerDTO;
import com.pio.foodiepanda.model.Coupon;
import com.pio.foodiepanda.service.AdminService;
import com.pio.foodiepanda.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/create-coupon")
    public ResponseEntity<String> createCoupon(@RequestBody CouponDTO couponDTO) {
        Coupon createCoupon = couponService.createCoupon(couponDTO);
        return ResponseEntity.ok(MessageConstant.COUPON_CREATED_SUCCESSFULLY);
    }

    @GetMapping("/unapproved-owner")
    public ResponseEntity<List<RestaurantOwnerDTO>> getUnapprovedOwners() {
        List<RestaurantOwnerDTO> unApprovedOwners = adminService.getUnApprovedOwners();
        return ResponseEntity.ok(unApprovedOwners);
    }

    @GetMapping("/approved-owner")
    public ResponseEntity<List<RestaurantOwnerDTO>> getApprovedOwners() {
        List<RestaurantOwnerDTO> approvedOwners = adminService.getApprovedOwners();
        return ResponseEntity.ok(approvedOwners);
    }

    @PutMapping("/approve/{restaurantOwnerId}")
    public ResponseEntity<String> approveOwner(@PathVariable Long restaurantOwnerId) {
        adminService.approveOwner(restaurantOwnerId);
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }

    @PatchMapping("/reject/{restaurantOwnerId}")
    public ResponseEntity<?> rejectOwner(@PathVariable Long restaurantOwnerId) {
        adminService.rejectOwner(restaurantOwnerId);
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }
}
