package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.model.MenuItem;

import java.security.Principal;
import java.util.List;

public interface MenuItemService {

    void addMenuItem(MenuItemDTO menuItemDTO, Principal principal);

    List<MenuItemDTO> getMenuItemsForOwner(String ownerEmail);
}
