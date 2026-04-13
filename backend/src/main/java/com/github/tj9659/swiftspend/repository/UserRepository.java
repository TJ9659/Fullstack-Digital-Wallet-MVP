package com.github.tj9659.swiftspend.repository;

import com.github.tj9659.swiftspend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
