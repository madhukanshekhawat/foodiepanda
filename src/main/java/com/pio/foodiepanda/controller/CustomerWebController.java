package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/customer")
public class CustomerWebController {

    @GetMapping("/dashboard")
    public String restaurantDashboard() {
        return "customer/base";
    }
}
