package com.pio.foodiepanda.configuartion;

import com.pio.foodiepanda.constants.ApiConstant;
import com.pio.foodiepanda.enums.UserRole;
import com.pio.foodiepanda.filter.AuthEntryPointJwt;
import com.pio.foodiepanda.filter.JwtAuthenticationFilter;
import com.pio.foodiepanda.service.impl.CustomUserDetailsService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtRequestFilter;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private JwtAuthenticationFilter roleBasedRedirectFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request
                .requestMatchers(SecurityConstant.INDEX, SecurityConstant.RESTAURANT_DETAILS, SecurityConstant.DASHBOARD).permitAll()
                .requestMatchers("/static/**", "/WEB-INF/views/**").permitAll()
                .requestMatchers(SecurityConstant.AUTHENTICATION_REST_APIS, SecurityConstant.REGISTRATION, SecurityConstant.LOGIN_REST_API).permitAll()
                .requestMatchers(SecurityConstant.CUSTOMER_REST_APIS, SecurityConstant.CUSTOMER_ORDER_API, SecurityConstant.CUSTOMER_ADDRESS_API, SecurityConstant.CUSTOMER_CART_API).hasAuthority(UserRole.CUSTOMER.name())
                .requestMatchers(SecurityConstant.RESTAURANT_APIS, SecurityConstant.RESTAURANT_REST_APIS, SecurityConstant.MENU, SecurityConstant.CATEGORY, SecurityConstant.COUPON).hasAuthority(UserRole.RESTAURANT_OWNER.name())
                .requestMatchers(SecurityConstant.ADMIN_APIS, SecurityConstant.ADMIN_REST_APIS).hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(SecurityConstant.LOGOUT_REST_API).hasAnyRole(UserRole.ADMIN.toString(), UserRole.RESTAURANT_OWNER.toString(), UserRole.CUSTOMER.toString())
        );
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(loginForm -> loginForm
                .loginPage(SecurityConstant.LOGIN)
                .successHandler(successHandler)
                .permitAll());
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(roleBasedRedirectFilter, UsernamePasswordAuthenticationFilter.class);
        http.logout(logout -> logout
                .logoutUrl(ApiConstant.LOGOUT)
                .logoutSuccessUrl(ApiConstant.LOGOUT_SUCCESS_URL)
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID", "JWT-TOKEN")
                .invalidateHttpSession(true)
                .deleteCookies(SecurityConstant.JWT_TOKEN));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}