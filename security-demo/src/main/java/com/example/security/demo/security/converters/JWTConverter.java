package com.example.security.demo.security.converters;

import com.example.security.demo.security.authentication.JWTTokenAuthentication;
import com.example.security.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class JWTConverter implements AuthenticationConverter {


    @Override
    public Authentication convert(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (JwtUtil.isValidToken(token)) {
                return JWTTokenAuthentication.builder()
                        .token(token)
                        .build();

            }
        }
        return null;
    }
}
