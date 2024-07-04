package com.revolversolutions.trainingmanagement.aspect;

import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.entity.UserActivity;
import com.revolversolutions.trainingmanagement.serviceImpl.UserActivityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class AuthenticationEventListener
        //implements ApplicationListener<AbstractAuthenticationEvent>
{
/*
    private final UserActivityService userActivityService;
    private final HttpServletRequest request;

    @Autowired
    public AuthenticationEventListener(UserActivityService userActivityService, HttpServletRequest request) {
        this.userActivityService = userActivityService;
        this.request = request;
    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSuccessEvent) {
            handleLoginSuccess((AuthenticationSuccessEvent) event);
        } else if (event instanceof LogoutSuccessEvent) {
            handleLogoutSuccess((LogoutSuccessEvent) event);
        }
    }

    private void handleLoginSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            logUserActivity(user, "login");
        }
    }

    private void handleLogoutSuccess(LogoutSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            logUserActivity(user, "logout");
        }
    }

    private void logUserActivity(User user, String action) {
        UserActivity activity = new UserActivity();
        activity.setAction(action);
        activity.setTimestamp(LocalDateTime.now());
        activity.setFullName(user.getFirstName() + " " + user.getLastName());
        activity.setIpAddress(request.getRemoteAddr());
        userActivityService.save(activity);
    }
 */
}




