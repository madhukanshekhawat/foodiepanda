package com.pio.foodiepanda.service;

import com.pio.foodiepanda.dto.UserDTO;

public interface LoginInfoService {

    UserDTO getAuthenticatedUser(String email);

}
