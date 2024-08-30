package com.example.security.demo.services;


import com.example.security.demo.entities.User;
import com.example.security.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;


    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        log.info("UserService.getAllUsers()");
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
