package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.AddressDTO;
import com.pio.foodiepanda.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(Principal principal) {
        String username = principal.getName();
        List<AddressDTO> addressDTOS = addressService.getAddressByUsername(username);
        return ResponseEntity.ok(addressDTOS);
    }

    @PostMapping("/address/add")
    public ResponseEntity<AddressDTO> addAddress(Principal principal, @RequestBody AddressDTO addressDTO) {
        String username = principal.getName();
        AddressDTO savedAddress = addressService.addNewAddress(username, addressDTO);
        return ResponseEntity.ok(savedAddress);
    }


}

