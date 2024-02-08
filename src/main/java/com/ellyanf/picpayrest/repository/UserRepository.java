package com.ellyanf.picpayrest.repository;

import com.ellyanf.picpayrest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByDocument(String document);
    boolean existsByEmail(String email);
}
