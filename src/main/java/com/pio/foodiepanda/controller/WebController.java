package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class WebController {

    @GetMapping("/base")
    public String base() {
        return ViewConstant.ADMIN_BASE;
    }

    @GetMapping("/dashboard")
    public String admin() {
        return ViewConstant.ADMIN_DASHBOARD;
    }

    @GetMapping("/view-restaurant")
    public String viewRestaurant() {
        return ViewConstant.VIEW_RESTAURANTS;
    }

    @GetMapping("/view-customer")
    public String viewCustomer() {
        return ViewConstant.VIEW_CUSTOMERS;
    }

    @GetMapping("/approve-owners")
    public String viewUnapprovedOwners() {
        return ViewConstant.APPROVE_OWNERS;
    }

    @GetMapping("/approved-owners")
    public String viewOwner() {
        return ViewConstant.APPROVED_OWNERS;
    }

    @GetMapping("/create-coupon")
    public String createCoupon() {
        return ViewConstant.CREATE_COUPON;
    }
}
