package com.revolversolutions.trainingmanagement.repository;


import com.revolversolutions.trainingmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailOrPhone(String email, String phone);
    Optional<User> findUserByUserName(String userName);


}
