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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    Logger logger = Logger.getLogger(AddressServiceImpl.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    private static void extracted(AddressDTO addressDTO, Address address, AddressRepository addressRepository) {
        address.setAddressLine(addressDTO.getAddressLine());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setAddressLabel(addressDTO.getLabel());
        addressRepository.save(address);
    }

    /**
     * Retrieves a list of addresses for a given username.
     *
     * @param username the username to search for.
     * @return a list of AddressDTO objects.
     */
    @Override
    public List<AddressDTO> getAddressByUsername(String username) {
        logger.log(Level.INFO, MessageConstant.FETCHING_ADDRESSES, username);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.log(Level.INFO, MessageConstant.USER_NOT_FOUND, username);
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

    /**
     * Adds a new address for a given username.
     *
     * @param username   the username to add the address for.
     * @param addressDTO the address details.
     * @return the saved AddressDTO object.
     */
    @Override
    public AddressDTO addNewAddress(String username, AddressDTO addressDTO) {
        logger.log(Level.INFO, MessageConstant.ADDING_ADDRESS, username);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.log(Level.INFO, MessageConstant.USER_NOT_FOUND, username);
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
        logger.log(Level.INFO, MessageConstant.SUCCESSFUL_MESSAGE, username);

        return convertToDTO(savedAddress);
    }

    /**
     * Updates an existing address.
     *
     * @param addressId  the ID of the address to update.
     * @param addressDTO the new address details.
     * @param email      the email of the user.
     */
    @Override
    public void updateAddress(Long addressId, AddressDTO addressDTO, String email) {
        logger.log(Level.INFO, MessageConstant.UPDATING_ADDRESS + addressId + MessageConstant.FOR_EMAIL + email);
        User user = userRepository.findByEmail(email);
        if (user == null || user.getCustomer() == null) {
            logger.log(Level.INFO, MessageConstant.USER_NOT_FOUND, email);
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ADDRESS_NOT_FOUND));

        if (!address.getUser().getId().equals(user.getId())) {
            logger.log(Level.INFO, MessageConstant.USER_NOT_FOUND, email);
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }

        extracted(addressDTO, address, addressRepository);
        logger.log(Level.INFO, MessageConstant.SUCCESSFUL_MESSAGE, addressId);
    }

    /**
     * Converts an Address entity to an AddressDTO.
     *
     * @param address the Address entity.
     * @return the AddressDTO object.
     */
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