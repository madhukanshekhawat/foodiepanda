package com.pio.foodiepanda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class WebController {

    @GetMapping("/base")
    public String base(){
        return "admin/base";
    }

    @GetMapping("/dashboard")
    public String admin(){
        return "admin/admin_dashboard";
    }

    @GetMapping("/view-Restaurant")
    public String viewRestaurant(){
        return "admin/view-restaurants";
    }

    @GetMapping("/view-customer")
    public String viewCustomer(){
        return "admin/view-customers";
    }
}
