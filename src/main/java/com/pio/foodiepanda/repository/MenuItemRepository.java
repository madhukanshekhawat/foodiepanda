package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}
