package com.pio.foodiepanda.controller;


import com.pio.foodiepanda.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/restaurant")
public class RestaurantOwnerWebController {

    @GetMapping("/dashboard")
    public String restaurantDashboard() {
        return ViewConstant.RESTAURANT_DASHBOARD;
    }

    @GetMapping("/view-coupon")
    public String dashboard() {
        return ViewConstant.VIEW_COUPON;
    }

    @GetMapping("/addMenu")
    public String addMenuItem() {
        return ViewConstant.ADD_MENU;
    }

    @GetMapping("/view-menu")
    public String viewMenuItem() {
        return ViewConstant.VIEW_MENU;
    }

    @GetMapping("/manage-order")
    public String viewOrder() {
        return "restaurantowner/view-order-page";
    }

    @GetMapping("/add-category")
    public String addCategory() {
        return "restaurantowner/add-category";
    }

    @GetMapping("/view-category")
    public String viewCategory() {
        return "restaurantowner/view-category";
    }

    @GetMapping("/view-profile")
    public String viewProfile(){
        return "restaurantowner/change-availability";
    }
}

