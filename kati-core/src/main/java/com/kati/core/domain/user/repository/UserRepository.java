package com.kati.core.domain.user.repository;

import com.kati.core.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsBySecondEmail(String secondEmail);

    Optional<User> findByEmail(String email);

    Optional<User> findBySecondEmail(String secondEmail);
}
