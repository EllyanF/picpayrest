package com.ellyanf.picpayrest.service;

import com.ellyanf.picpayrest.domain.User;
import com.ellyanf.picpayrest.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(@NotNull User user) {
        return userRepository.save(user);
    }

    public User findByDocument(String document) {
        return userRepository.findByDocument(document).orElseThrow();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
