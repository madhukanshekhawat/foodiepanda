package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.RestaurantRegisterDTO;
import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.model.*;
import com.pio.foodiepanda.repository.*;
import com.pio.foodiepanda.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.pio.foodiepanda.constants.MessageConstant.INVALID_ROLE;
import static com.pio.foodiepanda.enums.DeliveryAddressLabel.RESTAURANT;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    Logger logger = Logger.getLogger(RegistrationServiceImpl.class.getName());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestaurantAddressRepository restaurantAddressRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * Registers a new user based on UserDTO
     * @param : userDTO contain the user information
     * @throws : throws the exception when the role specified in UserDTO is invalid
     */
    @Override
    public Long registerUser(UserDTO userDTO) throws Exception {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);

        if (userDTO.getRole().equals("RESTAURANT_OWNER")) {
            RestaurantOwner restaurantOwner = new RestaurantOwner();
            restaurantOwner.setUser(user);
            restaurantOwner.setFirstName(userDTO.getFirstName());
            restaurantOwner.setLastName(userDTO.getLastName());
            restaurantOwner.setPhoneNumber(userDTO.getPhoneNumber());
            restaurantOwner.setApproved(false);
            restaurantOwnerRepository.save(restaurantOwner);
            return restaurantOwner.getOwnerID();
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

        return user.getId(); // Return the user ID
    }

    @Override
    public void registerRestaurant(RestaurantRegisterDTO restaurantRegisterDTO) throws Exception {

        Long restaurantOwnerId = restaurantRegisterDTO.getOwnerId();
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(restaurantRegisterDTO.getOwnerId())
                .orElseThrow(() -> new Exception(MessageConstant.RESTAURANT_NOT_FOUND));
        RestaurantAddress restaurantAddress = new RestaurantAddress();
        RestaurantOwner owner = new RestaurantOwner();
        restaurantOwner.setOwnerID(restaurantRegisterDTO.getOwnerId()); // Assuming getOwnerId() returns the ID
        restaurantAddress.setRestaurantOwner(restaurantOwner);
        restaurantAddress.setAddressLine(restaurantRegisterDTO.getAddressLine());
        restaurantAddress.setCity(restaurantRegisterDTO.getCity());
        restaurantAddress.setState(restaurantRegisterDTO.getState());
        restaurantAddress.setPostalCode(restaurantRegisterDTO.getPostalCode());
        restaurantAddress.setAddressLabel(RESTAURANT);

        restaurantAddressRepository.save(restaurantAddress);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRegisterDTO.getRestaurantName());
        restaurant.setPhoneNumber(restaurantRegisterDTO.getRestaurantContact());
        restaurant.setRestaurantAddress(restaurantAddress);
        restaurant.setRestaurantOwner(restaurantOwner);

        restaurantRepository.save(restaurant);

        restaurantOwner.setRestaurant(restaurant);
        restaurantOwnerRepository.save(restaurantOwner);
    }
}

