package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads the user details for authentication based on the provided username(email)
     *
     * @param username: the email of the user to be loaded
     * @return an instance of user details contains user info
     * @throws User not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.info(MessageConstant.USER_NOT_FOUND);
        }
        return new CustomUserDetails(user);
    }
}