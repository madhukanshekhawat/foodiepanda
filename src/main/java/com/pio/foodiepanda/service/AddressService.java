package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    List<AddressDTO> getAddressByUsername(String username);

    AddressDTO addNewAddress(String username, AddressDTO addressDTO);

    void updateAddress(Long addressId, AddressDTO addressDTO, String email);
}
