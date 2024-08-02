package com.revolversolutions.trainingmanagement.repository;


import com.revolversolutions.trainingmanagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailOrPhone(String email, String phone);
    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByUserId(String userId);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.password= ?2 where u.email = ?1")
    void updatePassword(String email, String password);
    User findUserByEmail(String email);
    User findByUserName(String userName);
    void deleteByUserId(String userId);

    Optional<User> findUserByEmailIgnoreCase(String email);
    Optional<User> existsByEmail(String email);



}
