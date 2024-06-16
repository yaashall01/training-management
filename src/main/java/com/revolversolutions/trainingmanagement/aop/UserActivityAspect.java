package com.revolversolutions.trainingmanagement.aop;


import com.revolversolutions.trainingmanagement.entity.UserActivity;
import com.revolversolutions.trainingmanagement.serviceImpl.UserActivityService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class UserActivityAspect {

    private final UserActivityService userActivityService;

    public UserActivityAspect(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @Pointcut("execution(* com.revolversolutions.trainingmanagement.controller..*(..)) && @annotation(UserActivityLog)")
    public void userActivityMethods() {}

    @AfterReturning("userActivityMethods() && @annotation(userActivityLog)")
    public void logUserActivity(UserActivityLog userActivityLog) {
        UserActivity activity = new UserActivity();
        activity.setAction(userActivityLog.action());
        activity.setTimestamp(LocalDateTime.now());
        // additional details like user ID, IP address, etc.
        userActivityService.save(activity);
    }
}
