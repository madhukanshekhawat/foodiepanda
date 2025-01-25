package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/add")
    public ResponseEntity<String> addMenuItem(@RequestBody MenuItemDTO menuItemDTO, Principal principal) {
        menuItemService.addMenuItem(menuItemDTO,principal);
        return ResponseEntity.ok("Menu Item added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsForOwner(Principal principal){
        String ownerEmail = principal.getName();
        List<MenuItemDTO> menuItems = menuItemService.getMenuItemsForOwner(ownerEmail);
        return ResponseEntity.ok(menuItems);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItemDTO> updateMenuItemAvailability(@PathVariable Long id, @RequestParam boolean available, Principal principal){
        String ownerEmail = principal.getName();
        MenuItemDTO updateMenuItem = menuItemService.updateMenuItemAvailability(id,available,ownerEmail);
        return ResponseEntity.ok(updateMenuItem);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long menuItemId, Principal principal){
        menuItemService.deleteMenuItem(menuItemId, principal);
        return ResponseEntity.ok("Deleted Successfully");
    }
}