package com.example.security.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

public class JwtUtil {

    // Secret key for signing and verifying JWTs
    private static final String SECRET = "9L55oT6uSfi9qKQZNIhmy6ZLgfcgwpq3";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // Generate JWT token
    public static String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .issuer("Admin")
                .subject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 30000000))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extract username from the token
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from the token
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Check if the token is expired
    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token
    public static Boolean isValidToken(String token) {
        return !isTokenExpired(token);
    }

    // Extract specific claim from the token
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private static Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Set the key used for signing
                .build()
                .parseClaimsJws(jwt)  // Parse the JWT and extract claims
                .getBody();
    }


}
