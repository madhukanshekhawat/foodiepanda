package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.UserRepository;
import com.pio.foodiepanda.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getAuthenticatedUser(String email) {
        User user = userRepository.findByEmail(email);
        UserDTO userDto = null;
        switch (user.getRole()) {
            case ADMIN:
                userDto = new UserDTO();
                if (user.getAdmin() != null) {
                    userDto.setName(user.getAdmin().getFirstName() + " " + user.getAdmin().getLastName());
                } else {
                    userDto.setName("Admin not found");
                }
                userDto.setRole(user.getRole().toString());
                break;
            case RESTAURANT_OWNER:
                userDto = new UserDTO();
                userDto.setName(user.getRestaurantOwner().getFirstName() + " " + user.getRestaurantOwner().getLastName());
                userDto.setRole(user.getRole().toString());
                userDto.setRestaurantName(user.getRestaurantOwner().getRestaurant().getName());
                userDto.setApproved(user.getRestaurantOwner().isApproved());
                break;
            case CUSTOMER:
                userDto = new UserDTO();
                userDto.setName(user.getCustomer().getFirstName() + " " + user.getCustomer().getLastName());
                userDto.setRole(user.getRole().toString());
                break;
        }
        if (userDto == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userDto;
    }

}
