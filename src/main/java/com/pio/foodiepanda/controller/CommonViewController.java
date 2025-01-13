package com.pio.foodiepanda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonViewController {

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/approval-pending")
    public String approvalPending() {
        return "approval-pending";
    }

}
