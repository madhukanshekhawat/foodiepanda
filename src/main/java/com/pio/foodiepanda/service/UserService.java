package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO) throws Exception;
}
