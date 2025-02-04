package com.pio.foodiepanda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/user/status")
    public ResponseEntity<?> checkLoginStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);

        return ResponseEntity.ok().body(new LoginStatusResponse(isLoggedIn));
    }

    public static class LoginStatusResponse {
        private boolean loggedIn;

        public LoginStatusResponse(boolean loggedIn) {
            this.loggedIn = loggedIn;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public void setLoggedIn(boolean loggedIn) {
            this.loggedIn = loggedIn;
        }
    }
}
