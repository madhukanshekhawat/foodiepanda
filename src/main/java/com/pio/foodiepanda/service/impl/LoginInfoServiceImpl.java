package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.dto.UserDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.UserRepository;
import com.pio.foodiepanda.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pio.foodiepanda.enums.UserRole.RESTAURANT_OWNER;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getAuthenticatedUser(String email) {
        User loginInfo = userRepository.findByEmail(email);
        UserDTO userDto = null;

        switch (loginInfo.getRole()) {


            case RESTAURANT_OWNER:
                userDto = new UserDTO();
                userDto.setRestaurantName(userDto.getRestaurantName());
                userDto.setRole(userDto.getRole());
                break;

        }
        if (userDto == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return userDto;
    }
    }
