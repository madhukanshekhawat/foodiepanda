package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.exception.InvalidUserDataException;
import com.pio.foodiepanda.exception.UserAlreadyExistsException;
import com.pio.foodiepanda.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegistrationService userService;

    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            System.out.println("Received UserDTO: " + userDTO);
            userService.registerUser(userDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } catch (InvalidUserDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
