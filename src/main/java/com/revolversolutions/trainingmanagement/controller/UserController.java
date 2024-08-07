package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.UserRequest;
import com.revolversolutions.trainingmanagement.dto.UserResponse;
import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Page<UserResponse>> gelAllUsers(Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String userId, @Valid @RequestBody UserRequest userRequest)
        throws IOException{
        UserResponse userResponse = userService.updateUser(userId, userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/enrollments")
    public ResponseEntity<List<EnrollmentDTO>> getUserEnrollments(
            @PathVariable String userId) {
        List<EnrollmentDTO> enrollments = userService.getUserEnrollments(userId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping("/{userId}/profile-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable String userId,
                                                @RequestParam("file") MultipartFile file) {
        try {
            userService.uploadUserProfileImage(userId, file);
            return ResponseEntity.status(HttpStatus.OK).body("Profile image uploaded successfully");
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile image");
        }

    }

    @GetMapping("/{userId}/profile-image")
    public ResponseEntity<FileDB> getUserProfileImage(@PathVariable String userId) {
        try {
            FileDB fileDB = userService.getUserProfileImage(userId);
            return ResponseEntity.status(HttpStatus.FOUND).body(fileDB);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> authenticatedUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        UserResponse userResponse = userService.getUserById(currentUser.getUserId());
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/{userId}/programs/{programId}/enroll")
    public ResponseEntity<EnrollmentDTO> enrollProgram(@PathVariable String userId, @PathVariable String programId) {
        EnrollmentDTO enrollmentDTO = userService.enrollProgram(userId, programId);
        return ResponseEntity.ok(enrollmentDTO);
    }


    /*
    @PostMapping("/{userId}/profile-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable String userId, @RequestParam("file") MultipartFile file) throws IOException {

        try {
            userService.uploadProfileImage(userId, file);
            return ResponseEntity.status(HttpStatus.OK).body("Profile image uploaded successfully");
        }catch(IOException a){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile image");
        }
    }

    @GetMapping("/{userId}/profile-image")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String userId) {
        try {
            byte[] imageData = userService.getUserProfileImage(userId);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(imageData);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

 */



}
