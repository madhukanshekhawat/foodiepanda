package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.enums.DeliveryAddressLabel;
import com.pio.foodiepanda.model.*;
import com.pio.foodiepanda.repository.CustomerRepository;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import com.pio.foodiepanda.repository.UserRepository;
import com.pio.foodiepanda.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.pio.foodiepanda.constants.MessageConstant.INVALID_ROLE;
import static com.pio.foodiepanda.enums.DeliveryAddressLabel.RESTAURANT;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = Logger.getLogger(RegistrationServiceImpl.class.getName());

    /*
     * Registers a new user based on UserDTO
     * @param : userDTO contain the user information
     * @throws : throws the exception when the role specified in UserDTO is invalid
     */
    @Override
    public void registerUser(UserDTO userDTO) throws Exception {

        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);

        if (userDTO.getRole().equals("RESTAURANT_OWNER")) {
            RestaurantOwner restaurantOwner = new RestaurantOwner();
            restaurantOwner.setUser(user);
            restaurantOwner.setFirstName(userDTO.getFirstName());
            restaurantOwner.setLastName(userDTO.getLastName());
            restaurantOwner.setPhoneNumber(userDTO.getPhoneNumber());
            restaurantOwner.setApproved(false); // Default value

            Address address = new Address();
            address.setAddressLine(userDTO.getAddressLine());
            address.setCity(userDTO.getCity());
            address.setState(userDTO.getState());
            address.setPostalCode(userDTO.getZipCode());
            address.setAddressLabel(RESTAURANT);

            // Create and set restaurant details
            Restaurant restaurant = new Restaurant();
            restaurant.setName(userDTO.getRestaurantName());
            restaurant.setPhoneNumber(userDTO.getRestaurantContact());
            restaurant.setAddress(address);
            restaurant.setRestaurantOwner(restaurantOwner);

            restaurantOwnerRepository.save(restaurantOwner);

            // Debugging statements
            logger.log(Level.INFO, "First Name:" + userDTO.getFirstName());
            logger.log(Level.INFO, "Last Name:" + userDTO.getLastName());
            logger.log(Level.INFO, "Phone Number:" + userDTO.getPhoneNumber());

            restaurantOwnerRepository.save(restaurantOwner);

        } else if (userDTO.getRole().equals("CUSTOMER")) {
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setFirstName(userDTO.getFirstName());
            customer.setLastName(userDTO.getLastName());
            customer.setPhoneNumber(userDTO.getPhoneNumber());
            customerRepository.save(customer);
        } else {
            throw new Exception(INVALID_ROLE);
        }
    }
}
