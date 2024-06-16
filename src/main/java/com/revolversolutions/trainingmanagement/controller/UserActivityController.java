package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.entity.UserActivity;
import com.revolversolutions.trainingmanagement.serviceImpl.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("ADMIN")
public class UserActivityController {

    private final UserActivityService userActivityService;

    @Autowired
    public UserActivityController(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @GetMapping("/user-activities")
    public List<UserActivity> getUserActivities() {
        return userActivityService.getAllActivities();
    }
}