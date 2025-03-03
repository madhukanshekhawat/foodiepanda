package com.pio.foodiepanda.controller;


import com.pio.foodiepanda.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurant")
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
        return ViewConstant.VIEW_ORDER;
    }

    @GetMapping("/add-category")
    public String addCategory() {
        return ViewConstant.ADD_CATEGORY;
    }

    @GetMapping("/view-category")
    public String viewCategory() {
        return ViewConstant.VIEW_CATEGORY;
    }

    @GetMapping("/change-availability")
    public String viewProfile() {
        return ViewConstant.CHANGE_AVAILABILITY;
    }

    @GetMapping("/profile-page")
    public String profilePage(){
        return ViewConstant.PROFILE_PAGE;
    }
}

