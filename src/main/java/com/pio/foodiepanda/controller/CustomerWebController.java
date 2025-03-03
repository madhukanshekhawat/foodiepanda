package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/customer")
public class  CustomerWebController {

    @GetMapping("/dashboard")
    public String restaurantDashboard() {
        return ViewConstant.CUSTOMER_DASHBOARD;
    }

    @GetMapping("/restaurant-detail")
    public String restaurantViewController() {
        return ViewConstant.CUSTOMER_RESTAURANT_DETAIL;
    }

    @GetMapping("/cart")
    public String cart() {
        return ViewConstant.CART;
    }

    @GetMapping("/order-status")
    public String checkout() {
        return ViewConstant.CHECKOUT;
    }

    @GetMapping("/order-summary")
    public String orderSummary() {
        return ViewConstant.ORDER_SUMMARY;
    }

    @GetMapping("/show-address")
    public String showAddress() {
        return ViewConstant.SHOW_ORDER;
    }

    @GetMapping("/profile-page")
    public String profilePage() {
        return ViewConstant.PROFILE;
    }

}
