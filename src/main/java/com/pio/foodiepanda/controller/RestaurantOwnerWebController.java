package com.pio.foodiepanda.controller;


import com.pio.foodiepanda.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurant")
public class RestaurantOwnerWebController {

    @GetMapping("/view-coupon")
    public String dashboard() {
        return "restaurantowner/view-coupon";
    }

    @GetMapping("/addMenu")
    public String addMenuItem() {
        return "restaurantowner/addMenu";
    }
}
