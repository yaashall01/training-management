package com.revolversolutions.trainingmanagement.aspect;


import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.entity.UserActivity;
import com.revolversolutions.trainingmanagement.serviceImpl.UserActivityService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class UserActivityAspect {

    private final UserActivityService userActivityService;
    private final HttpServletRequest request;

    public UserActivityAspect(UserActivityService userActivityService, HttpServletRequest request) {
        this.userActivityService = userActivityService;
        this.request = request;
    }

    @Pointcut("execution(* com.revolversolutions.trainingmanagement.controller..*(..)) && @annotation(UserActivityLog)")
    public void userActivityMethods() {}

    @AfterReturning("userActivityMethods() && @annotation(userActivityLog)")
    public void logUserActivity(UserActivityLog userActivityLog) {
        UserActivity activity = new UserActivity();
        activity.setAction(userActivityLog.action());
        activity.setActionType(userActivityLog.actionType());
        activity.setTimestamp(LocalDateTime.now());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User){
            User user = (User) authentication.getPrincipal();
            activity.setFullName(user.getFirstName() + " " + user.getLastName());
        }

        String ipAddress = request.getRemoteAddr();
        activity.setIpAddress(ipAddress);

        userActivityService.save(activity);
    }
}
