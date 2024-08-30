package com.example.security.demo.security.configurer;

import com.example.security.demo.security.converters.JWTConverter;
import com.example.security.demo.security.filters.JWTAuthenticationFilter;
import com.example.security.demo.security.filters.JWTValidatorFilter;
import com.example.security.demo.security.providers.JWTAuthenticationProvider;
import com.example.security.demo.security.userdetails.JpaUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends AbstractHttpConfigurer<JWTConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        JpaUserDetailsService userDetailsService = http
                .getSharedObject(ApplicationContext.class)
                .getBean(JpaUserDetailsService.class);


        JWTAuthenticationProvider jwtAuthenticationProvider = new JWTAuthenticationProvider(userDetailsService);

        http.authenticationProvider(jwtAuthenticationProvider);
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        AuthenticationConverter authenticationConverter = new JWTConverter();


        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager);
        JWTValidatorFilter jwtValidatorFilter = new JWTValidatorFilter(authenticationManager, authenticationConverter);

        http.addFilter(jwtAuthenticationFilter);
        http.addFilterBefore(jwtValidatorFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
