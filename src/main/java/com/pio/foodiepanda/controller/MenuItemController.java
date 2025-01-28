package com.pio.foodiepanda.controller;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.model.MenuItem;
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
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
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
        return ResponseEntity.ok(MessageConstant.SUCCESSFUL_MESSAGE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id){
        MenuItemDTO menuItemDTO = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(menuItemDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemDTO menuItemDTO){
        String message = menuItemService.updateMenuItem(id,menuItemDTO);
        return ResponseEntity.ok(message);
    }
}