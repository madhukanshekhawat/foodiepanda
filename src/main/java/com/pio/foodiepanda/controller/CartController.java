package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CartItemDTO;
import com.pio.foodiepanda.dto.CartSyncRequest;
import com.pio.foodiepanda.model.Cart;
import com.pio.foodiepanda.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/items")
    public ResponseEntity<List<CartItemDTO>> getCartItems(Principal principal){
        String email = principal.getName();

        List<CartItemDTO> cartItemDTO = cartService.getCartItemForUser(email);
        return ResponseEntity.ok(cartItemDTO);
    }

    @PostMapping("/sync")
    public ResponseEntity<String> syncLocalCartToDB(@RequestBody List<CartItemDTO> cartItemDTO, Principal principal){
        try{
             cartService.saveCartItems(cartItemDTO,principal);
            return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
        } catch (RuntimeException e ){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

}
