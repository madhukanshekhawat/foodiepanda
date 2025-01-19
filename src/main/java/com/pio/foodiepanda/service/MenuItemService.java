package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.model.MenuItem;

import java.util.List;

public interface MenuItemService {

    MenuItem addMenuItem(MenuItemDTO menuItemDTO);

    List<MenuItem> getMenuItemsByRestaurants(Long restaurantId);
}
