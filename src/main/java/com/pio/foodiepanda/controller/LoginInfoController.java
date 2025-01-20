package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.LoginRequest;
import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    @PostMapping("/authenticated-user")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody LoginRequest loginRequest) {
        UserDTO user = loginInfoService.getAuthenticatedUser(loginRequest.getEmail());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/authenticated-user")
    public ResponseEntity<UserDTO> getAuthenticatedUser(Principal principal) {
        String email = principal.getName();
        UserDTO user = loginInfoService.getAuthenticatedUser(email);
        return ResponseEntity.ok().body(user);
    }
}
