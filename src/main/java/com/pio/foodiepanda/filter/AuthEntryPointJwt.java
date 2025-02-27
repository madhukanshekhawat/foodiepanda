package com.pio.foodiepanda.filter;

import com.pio.foodiepanda.constants.MessageConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Send a 401 Unauthorized response
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageConstant.UNAUTHORIZED);
    }

}
