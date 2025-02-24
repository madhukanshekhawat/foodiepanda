package com.pio.foodiepanda.configuartion;

public interface SecurityConstant {
    String JWT_TOKEN = "jwt-token";
    String LOGOUT_SUCCESSFUL_MESSAGE = "Logged out successfully";
    String LOGIN = "/api/user-login";
    String LOGOUT_REST_API = "/logout";

    String INDEX = "/api/customer/dashboard";
    String RESTAURANT_DETAILS = "/api/customer/restaurant-detail";
    String AUTHENTICATION_REST_APIS = "/api/authenticated-user";
    String REGISTRATION = "/api/registration";

    String LOGIN_REST_API = "/auth/login";
    String CUSTOMER_REST_APIS = "/api/customers/**";
    String RESTAURANT_APIS = "/restaurant/**";
    String RESTAURANT_REST_APIS = "/api/restaurant/**";
    String ADMIN_APIS = "/api/admin/**";
    String ADMIN_REST_APIS = "/api/admin/**";
    String DASHBOARD = "/api/customer/**";
    String CUSTOMER_ORDER_API = "/order/**";
    String CUSTOMER_ADDRESS_API = "/api/user/**";
    String CUSTOMER_CART_API = "/api/cart/**";
    String ORDER = "/order/**" ;
    String MENU = "/menu/**";
    String CATEGORY = "/categories/**";
    String COUPON = "/coupon/**";

}