package com.app.librarymanagement.services.impl;

import com.app.librarymanagement.models.Role;
import com.app.librarymanagement.models.User;
import com.app.librarymanagement.repositories.RoleRepository;
import com.app.librarymanagement.repositories.UserRepository;
import com.app.librarymanagement.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Override
    public void register(String username, String password) {
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if(existingUser != null) {
            throw new RuntimeException("Username is not available.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByName("ROLE_USER");
        if(userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setRoles(List.of(userRole));
        userRepository.save(newUser);
    }
}