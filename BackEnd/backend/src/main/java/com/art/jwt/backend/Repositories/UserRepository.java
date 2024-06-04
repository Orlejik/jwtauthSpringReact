package com.art.jwt.backend.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.art.jwt.backend.Enteties.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
