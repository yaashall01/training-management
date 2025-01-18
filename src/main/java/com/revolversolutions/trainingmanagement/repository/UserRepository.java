package com.revolversolutions.trainingmanagement.repository;


import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.enums.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailOrPhone(String email, String phone);
    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByUserId(String userId);

    Optional<User> findByEmail(String email);

    Page<User> findAllByUserRole(UserRole role, Pageable pageable);

    List<User> findAllByUserRole(UserRole role);


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
