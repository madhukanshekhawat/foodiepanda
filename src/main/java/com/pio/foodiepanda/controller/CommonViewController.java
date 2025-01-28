package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonViewController {

    @GetMapping("/registration")
    public String registration() {
        return ViewConstant.REGISTRATION;
    }

    @GetMapping("/approval-pending")
    public String approvalPending() {
        return ViewConstant.APPROVAL_PENDING;
    }

}
