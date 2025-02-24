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
import java.util.Map;

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
    public ResponseEntity<String> syncLocalCartToDB(@RequestBody Map<Long, Integer> localCart, Principal principal) {
        try {
            cartService.saveCartItems(localCart, principal);
            return ResponseEntity.ok("Cart synchronized successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error synchronizing cart");
        }
    }

    @DeleteMapping("/remove-item")
    public ResponseEntity<String> deleteCartItem(@RequestParam("menuItemId") Long menuItemId, Principal principal) {
        try {
            boolean isRemoved = cartService.removeItemFromCart(principal, menuItemId);
            if (!isRemoved) {
                return ResponseEntity.status(500).body("Error removing item from cart");
            }
            return ResponseEntity.ok("Item removed from cart successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error removing item from cart");
        }
    }


    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Principal principal){
        try{
            cartService.clearCartForUser(principal);
            return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstant.UNEXPECTED_ERROR);
        }
    }
}
