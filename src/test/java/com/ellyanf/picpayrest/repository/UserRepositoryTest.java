package com.ellyanf.picpayrest.repository;

import com.ellyanf.picpayrest.domain.User;
import com.ellyanf.picpayrest.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUserSuccessfully() {
        User user = new User();
        user.setName("Test");
        user.setDocument("111.111.111-11");
        user.setBalance(new BigDecimal(10000));
        user.setEmail("email@test.com");
        user.setPassword("12345");

        userRepository.save(user);

        assertTrue(userRepository.existsById(user.getId()));
        assertEquals(user, userRepository.findById(user.getId()).orElseThrow());
    }

    @Test
    void shouldSetUserTypeToCommon() {
        User user = new User();
        user.setName("Test");
        user.setDocument("111.111.111-11");
        user.setBalance(new BigDecimal(10000));
        user.setEmail("email@test.com");
        user.setPassword("12345");

        userRepository.save(user);

        assertEquals(UserType.COMMON, user.getUserType());
        assertTrue(userRepository.existsById(user.getId()));
    }

    @Test
    void shouldSetUserTypeToMerchant() {
        User user = new User();
        user.setName("Test");
        user.setDocument("11.111.111/1111-11");
        user.setBalance(new BigDecimal(10000));
        user.setEmail("email@test.com");
        user.setPassword("12345");

        userRepository.save(user);

        assertEquals(UserType.MERCHANT, user.getUserType());
        assertTrue(userRepository.existsById(user.getId()));
    }

    @Test
    void shouldThrowIfInvalidDocument() {
        User user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setDocument("11.111.111/1111");
        user.setBalance(new BigDecimal(10000));
        user.setEmail("email@test.com");
        user.setPassword("12345");

        assertThrows(RuntimeException.class, () -> userRepository.save(user));
    }
}