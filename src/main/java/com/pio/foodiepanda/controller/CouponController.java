package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.CouponStatus;
import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CouponDTO;
import com.pio.foodiepanda.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class  CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/all")
    public List<CouponDTO> getAllCouponForRestaurant(Principal principal) {
        String ownerUsername = principal.getName();
        return couponService.getCouponsByOwner(ownerUsername);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCouponStatus(@PathVariable Long id, @RequestParam("status") CouponStatus status, Principal principal) {
        String ownerUsername = principal.getName();
        couponService.updateCouponStatus(id, ownerUsername, status);
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }
}
