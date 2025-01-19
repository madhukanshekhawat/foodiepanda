package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.RestaurantDTO;
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
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = Logger.getLogger(RegistrationServiceImpl.class.getName());

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
    public void registerRestaurant(RestaurantDTO restaurantDTO) throws Exception {

        Long restaurantOwnerId = restaurantDTO.getOwnerId();
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(restaurantDTO.getOwnerId())
                .orElseThrow(() -> new Exception("Restaurant owner not found"));
        Address address = new Address();
        address.setAddressLine(restaurantDTO.getAddressDetails().getAddressLine());
        address.setCity(restaurantDTO.getAddressDetails().getCity());
        address.setState(restaurantDTO.getAddressDetails().getState());
        address.setPostalCode(restaurantDTO.getAddressDetails().getPostalCode());
        address.setAddressLabel(RESTAURANT);

        addressRepository.save(address);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setPhoneNumber(restaurantDTO.getPhoneNumber());
        restaurant.setAddress(address);
        restaurant.setRestaurantOwner(restaurantOwner);

        restaurantRepository.save(restaurant);
    }
}

