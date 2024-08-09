package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.ForgotPassword;
import com.revolversolutions.trainingmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {


    @Query("select fp from ForgotPassword fp where fp.otp= ?1 and fp.user= ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

    Optional<ForgotPassword> findByUser(User user);
}
