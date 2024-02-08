package com.ellyanf.picpayrest.service;

import com.ellyanf.picpayrest.domain.User;
import com.ellyanf.picpayrest.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(@NotNull User user) {
        validateUniqueConstraints(user);

        return userRepository.save(user);
    }

    private void validateUniqueConstraints(@NotNull User user) {
        if (userRepository.existsByDocument(user.getDocument())) throw new RuntimeException(
                "Document already registered: " + user.getDocument()
        );
        if (userRepository.existsByEmail(user.getEmail())) throw new RuntimeException(
                "Email already registered: " + user.getEmail()
        );
    }
}
