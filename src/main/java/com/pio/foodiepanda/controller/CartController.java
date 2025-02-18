package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CartItemDTO;
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
    public ResponseEntity<String> syncLocalCartToDB(@RequestBody List<CartItemDTO> localCart, Principal principal){
        try{
             cartService.saveCartItems(localCart,principal);
            return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
        } catch (RuntimeException e ){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @DeleteMapping("/remove-item")
    public ResponseEntity<String> deleteCartItem(@RequestParam("menuItemId") Long menuItemId, Principal principal) {
        boolean isRemoved = cartService.removeItemFromCart(principal, menuItemId);
        if (!isRemoved) {
            return ResponseEntity.status(500).body("Error removing item from cart");
        }
        return ResponseEntity.ok("Item removed from cart successfully");
    }
}
