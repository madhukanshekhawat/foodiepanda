package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.CustomerDTO;
import com.pio.foodiepanda.dto.UserDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAll();
}
