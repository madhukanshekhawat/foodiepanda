package com.pio.foodiepanda.utility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.WebUtils;

public class CookieUtil {

    /**
     * Clears the specified cookie by setting its value to null and max age to 0.
     *
     * @param response the HttpServletResponse to which the cookie will be added
     * @param name     the name of the cookie to be cleared
     * @param domain   the domain of the cookie to be cleared
     */
    public static void clear(HttpServletResponse response, String name, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain(domain);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * Retrieves the value of the specified cookie from the request.
     *
     * @param request the HttpServletRequest from which the cookie will be retrieved
     * @param name    the name of the cookie whose value is to be retrieved
     * @return the value of the cookie, or null if the cookie does not exist
     */
    public static String getValue(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return (cookie != null) ? cookie.getValue() : null;
    }
}
