package com.pio.foodiepanda.service;

import com.pio.foodiepanda.constants.JwtConstant;
import com.pio.foodiepanda.service.impl.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final byte[] decodedSecretKey;

    public JwtService() {
        decodedSecretKey = Base64.getUrlDecoder().decode(JwtConstant.SECRET_KEY);
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the given JWT token using the provided resolver function.
     *
     * @param token    the JWT token
     * @param resolver a function to resolve the claim from the Claims object
     * @param <T>      the type of the claim
     * @return the extracted claim
     */
    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token
     * @return the Claims object containing all claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(decodedSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token       the JWT token
     * @param userDetails the user details to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isValid(String token, CustomUserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && (!isTokenExpired(token));
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates a new JWT token for the given username with the specified validity duration.
     *
     * @param username         the username for which the token is generated
     * @param jwtValidDuration the validity duration of the token in hours
     * @return the generated JWT token
     */
    public String generateToken(String username, Integer jwtValidDuration) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, jwtValidDuration);
    }

    /**
     * Creates a new JWT token with the given claims, username, and validity duration.
     *
     * @param claims           the claims to be included in the token
     * @param username         the username for which the token is generated
     * @param jwtValidDuration the validity duration of the token in hours
     * @return the created JWT token
     */
    private String createToken(Map<String, Object> claims, String username, Integer jwtValidDuration) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * jwtValidDuration))
                .signWith(SignatureAlgorithm.HS256, decodedSecretKey)
                .compact();
    }
}
