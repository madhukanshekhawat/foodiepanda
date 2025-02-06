package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.ViewConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class CommonViewController {

    @GetMapping("/registration")
    public String registration() {
        return ViewConstant.REGISTRATION;
    }

    @GetMapping("/user-login")
    public String login() {
        return ViewConstant.LOGIN;
    }

    @GetMapping("/approval-pending")
    public String approvalPendingPage() {
        return ViewConstant.APPROVAL_PENDING;
    }

}
