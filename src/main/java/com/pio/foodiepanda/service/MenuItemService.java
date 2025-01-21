package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.MenuItemDTO;
import com.pio.foodiepanda.model.MenuItem;

import java.util.List;

public interface MenuItemService {

    String addMenuItem(MenuItem menuItem, String userEmail);
}
