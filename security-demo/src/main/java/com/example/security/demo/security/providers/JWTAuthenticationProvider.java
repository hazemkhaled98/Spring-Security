package com.example.security.demo.security.providers;

import com.example.security.demo.security.authentication.JWTTokenAuthentication;
import com.example.security.demo.services.UserService;
import com.example.security.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationProvider.class);


    private final UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String token = String.valueOf(authentication.getDetails());

        if(JwtUtil.isValidToken(token)) {

            log.info("Authentication Provider: JWT token is valid");
            String username = JwtUtil.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            log.info("Authentication Provider gets user details {}", userDetails);
            return JWTTokenAuthentication.builder()
                    .token(token)
                    .principal(userDetails)
                    .isAuthenticated(true)
                    .build();
        }

        log.info("Authentication Provider: JWT token is invalid");

        throw new BadCredentialsException("Invalid token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JWTTokenAuthentication.class);
    }
}
