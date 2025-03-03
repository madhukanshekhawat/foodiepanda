package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.CustomerDTO;

import java.security.Principal;
import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAll();

    CustomerDTO getCustomerProfile(Principal principal);

    String updateProfile(Principal principal, CustomerDTO customerDTO);
}
