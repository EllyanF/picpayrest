package com.ellyanf.picpayrest.repository;

import com.ellyanf.picpayrest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocument(String document);
}
