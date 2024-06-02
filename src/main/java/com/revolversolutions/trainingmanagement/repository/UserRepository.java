package com.revolversolutions.trainingmanagement.repository;


import com.revolversolutions.trainingmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailOrPhone(String email, String phone);
    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByUserId(String userId);

    void deleteByUserId(String userId);

    Optional<User> findUserByEmailIgnoreCase(String email);
    Optional<User> existsByEmail(String email);



}
