package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.CustomerDTO;
import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getAllUsers() {
        List<CustomerDTO> customers = customerService.getAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
