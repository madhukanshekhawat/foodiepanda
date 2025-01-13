package com.pio.foodiepanda.controller;

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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/create-coupon")
    public ResponseEntity<?> createCoupon(@RequestBody CouponDTO couponDTO) {
        Coupon createCoupon = couponService.createCoupon(couponDTO);
        return ResponseEntity.ok("Coupon Created Successfully!");
    }

    @GetMapping("/unapproved-owner")
    public List<RestaurantOwnerDTO> getUnapprovedOwners() {
        return adminService.getUnApprovedOwners();
    }

    @GetMapping("/approved-owner")
    public ResponseEntity<List<RestaurantOwnerDTO>> getApprovedOwners() {
        List<RestaurantOwnerDTO> approvedOwners = adminService.getApprovedOwners();
        return ResponseEntity.ok(approvedOwners);
    }

    @PostMapping("{restaurantOwnerId}/approve")
    public ResponseEntity<?> approveOwner(@PathVariable Long restaurantOwnerId) {
        adminService.approveOwner(restaurantOwnerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{restaurantOwnerId}/delete")
    public ResponseEntity<?> deleteOwner(@PathVariable Long restaurantOwnerId) {
        adminService.deleteOwner(restaurantOwnerId);
        return ResponseEntity.ok().build();
    }
}
