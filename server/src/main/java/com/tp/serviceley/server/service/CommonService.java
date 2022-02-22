package com.tp.serviceley.server.service;

import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommonService {
    private final UserRepository userRepository;

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findByEmail(userName).orElseThrow(() -> new BackendException("Invalid User Details."));
        return user;
    }
}
