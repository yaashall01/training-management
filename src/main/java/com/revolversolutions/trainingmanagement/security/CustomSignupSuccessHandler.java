package com.revolversolutions.trainingmanagement.security;


import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.entity.UserActivity;
import com.revolversolutions.trainingmanagement.serviceImpl.UserActivityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class CustomSignupSuccessHandler  {
//    private final UserActivityService userActivityService;
//    private final HttpServletRequest request;

//    public CustomSignupSuccessHandler(UserActivityService userActivityService, HttpServletRequest request) {
//        this.userActivityService = userActivityService;
//        this.request = request;
//    }

//    public void onSignupSuccess(User user) {
//        logUserActivity(user, "signup");
//    }

//    private void logUserActivity(User user, String action) {
//        UserActivity activity = new UserActivity();
//        activity.setAction(action);
//        activity.setTimestamp(LocalDateTime.now());
//        activity.setFullName(user.getFirstName() + " " + user.getLastName());
//        activity.setIpAddress(request.getRemoteAddr());
//        userActivityService.save(activity);
//    }
}
