package com.revolversolutions.trainingmanagement.repository;


import com.revolversolutions.trainingmanagement.aop.UserActivityAspect;
import com.revolversolutions.trainingmanagement.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
}
