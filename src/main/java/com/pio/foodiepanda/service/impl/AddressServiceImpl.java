package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.AddressDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.Address;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.AddressRepository;
import com.pio.foodiepanda.repository.UserRepository;
import com.pio.foodiepanda.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddressByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        List<Address> addresses = addressRepository.findByUserId(user.getId());
        return addresses.stream().map(address -> new AddressDTO(
                address.getAddressId(),
                address.getUser().getId(),
                address.getAddressLine(),
                address.getCity(),
                address.getState(),
                address.getAddressLabel(),
                address.getPostalCode()
        )).collect(Collectors.toList());

    }

    @Override
    public AddressDTO addNewAddress(String username, AddressDTO addressDTO) {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }

        Address address = new Address();
        address.setUser(user);
        address.setAddressLine(addressDTO.getAddressLine());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setAddressLabel(addressDTO.getLabel());
        address.setPostalCode(addressDTO.getPostalCode());

        Address savedAddress = addressRepository.save(address);

        return convertToDTO(savedAddress);

    }

    private AddressDTO convertToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setUserId(address.getUser().getId());
        addressDTO.setAddressLine(address.getAddressLine());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setLabel(address.getAddressLabel());
        addressDTO.setPostalCode(address.getPostalCode());
        return addressDTO;
    }

}