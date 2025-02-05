package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.dto.MenuItemResponse;

import java.security.Principal;
import java.util.List;

public interface MenuItemService {

    void addMenuItem(MenuItemDTO menuItemDTO, Principal principal);

    List<MenuItemDTO> getMenuItemsForOwner(String ownerEmail);

    MenuItemDTO updateMenuItemAvailability(Long id, boolean available, String ownerEmail);

    void deleteMenuItem(Long menuItemId, Principal principal);

    MenuItemDTO getMenuItemById(Long id);

    String updateMenuItem(Long id, MenuItemDTO menuItemDTO);

    List<MenuItemResponse> getAvailableMenuItems(int page, int size);

    List<String> searchAvailablemenuItem(String query);
}
