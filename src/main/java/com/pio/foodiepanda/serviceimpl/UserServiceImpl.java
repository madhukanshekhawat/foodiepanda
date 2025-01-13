package com.pio.foodiepanda.serviceimpl;

import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.model.Customer;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.CustomerRepository;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import com.pio.foodiepanda.repository.UserRepository;
import com.pio.foodiepanda.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

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

            // Debugging statements
            System.out.println("First Name: " + userDTO.getFirstName());
            System.out.println("Last Name: " + userDTO.getLastName());
            System.out.println("Phone Number: " + userDTO.getPhoneNumber());

            restaurantOwnerRepository.save(restaurantOwner);
        } else if (userDTO.getRole().equals("CUSTOMER")) {
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setFirstName(userDTO.getFirstName());
            customer.setLastName(userDTO.getLastName());
            customer.setPhoneNumber(userDTO.getPhoneNumber());
            customerRepository.save(customer);
        } else {
            throw new Exception("Invalid role");
        }
    }
}
