package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.aspect.UserActivityLog;
import com.revolversolutions.trainingmanagement.dto.UserRequest;
import com.revolversolutions.trainingmanagement.dto.UserResponse;
import com.revolversolutions.trainingmanagement.entity.UserActivity;
import com.revolversolutions.trainingmanagement.enums.ActionType;
import com.revolversolutions.trainingmanagement.service.UserService;
import com.revolversolutions.trainingmanagement.serviceImpl.UserActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserActivityService userActivityService;
    private final UserService userService;

    @Autowired
    public AdminController(UserActivityService userActivityService, UserService userService) {
        this.userActivityService = userActivityService;
        this.userService = userService;
    }

    @PostMapping("/create-user")
    @UserActivityLog(action = "Admin Create a User", actionType = ActionType.CREATE)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);

    }

    @PutMapping("/{userId}")
    @UserActivityLog(action = "Admin Update a User", actionType = ActionType.UPDATE)
    public ResponseEntity<UserResponse> updateUser(@PathVariable String userId, @Valid @RequestBody UserRequest userRequest)
            throws IOException {
        UserResponse userResponse = userService.updateUser(userId, userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/trainer")
    @UserActivityLog(action = "Admin Create a Trainer", actionType = ActionType.CREATE)
    public ResponseEntity<UserResponse> addTrainer(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.addTrainer(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/trainee")
    @UserActivityLog(action = "Admin Create a Trainee", actionType = ActionType.CREATE)
    public ResponseEntity<UserResponse> addTrainee(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.addTrainee(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/create-admin")
    @UserActivityLog(action = "Admin Create new Admin", actionType = ActionType.CREATE)
    public ResponseEntity<UserResponse> addAdmin(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.addAdmin(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    @UserActivityLog(action = "Admin Delete User", actionType = ActionType.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user-logs")
    public List<UserActivity> getUserActivities() {
        return userActivityService.getAllActivities();
    }

}
