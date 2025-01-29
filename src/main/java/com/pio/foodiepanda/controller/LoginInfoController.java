package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class LoginInfoController {
    @Autowired
    LoginInfoService loginInfoService;

    @GetMapping("/authenticated-user")
    public ResponseEntity<UserDTO> getAuthenticatedUser(Principal principal) {
        // current logged-in user email
        String email = principal.getName();
        UserDTO user = loginInfoService.getAuthenticatedUser(email);
        return ResponseEntity.ok().body(user);
    }
}

