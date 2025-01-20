package com.pio.foodiepanda.configuartion;

import com.pio.foodiepanda.constants.ApiConstant;
import com.pio.foodiepanda.enums.UserRole;
import com.pio.foodiepanda.model.Admin;
import com.pio.foodiepanda.model.RestaurantOwner;
import com.pio.foodiepanda.model.User;
import com.pio.foodiepanda.repository.AdminRepository;
import com.pio.foodiepanda.repository.RestaurantOwnerRepository;
import com.pio.foodiepanda.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = null;
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ADMIN.toString()))) {
            redirectUrl = ApiConstant.ADMIN_DASHBOARD_API;
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.RESTAURANT_OWNER.toString()))) {
            RestaurantOwner restaurantOwner = restaurantOwnerRepository.findByUserEmail(email);
            if (restaurantOwner != null && !restaurantOwner.isApproved()) {
                redirectUrl = ApiConstant.APPROVAL_PENDING_API;
            } else {
                redirectUrl = ApiConstant.RESTAURANT_DASHBOARD_API;
            }
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.CUSTOMER.toString()))) {
            redirectUrl = ApiConstant.CUSTOMER_DASHBOARD_API;
        }

        if(redirectUrl == null) {
            throw new IllegalStateException();
        }

        response.sendRedirect(redirectUrl);
    }
}
