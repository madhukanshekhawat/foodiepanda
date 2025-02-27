package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.JwtConstant;
import com.pio.foodiepanda.dto.LoginRequest;
import com.pio.foodiepanda.dto.LoginResponse;
import com.pio.foodiepanda.service.JwtService;
import com.pio.foodiepanda.service.LoginInfoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired 
    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService, LoginInfoService loginInfoService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws Exception {
        String token = null;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("Invalid username or password...");
            }
            token = jwtService.generateToken(loginRequest.getUsername(), JwtConstant.JWT_VALID_DURATION);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
