package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    public List<AddressDTO> getAddressByUsername(String username);

    AddressDTO addNewAddress(String username, AddressDTO addressDTO);
}
