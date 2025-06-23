package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.ImageMetadataDTO;
import com.revolversolutions.trainingmanagement.dto.user.UserRequest;
import com.revolversolutions.trainingmanagement.dto.user.UserResponse;
import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/trainers")
    public ResponseEntity<Page<UserResponse>> getAllTrainers(Pageable pageable){
        return ResponseEntity.ok(userService.getAllTrainers(pageable));
    }

    @GetMapping("/admins")
    public ResponseEntity<Page<UserResponse>> getAllAdmins(Pageable pageable){
        return ResponseEntity.ok(userService.getAllAdmins(pageable));
    }

    @GetMapping("/trainees")
    public ResponseEntity<Page<UserResponse>> getAllTrainees(Pageable pageable){
        return ResponseEntity.ok(userService.getAllTrainees(pageable));
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

    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<ImageMetadataDTO> uploadProfilePicture(@PathVariable String userId,
                                                                 @RequestParam("file") MultipartFile file) {
        ImageMetadataDTO uploadedImage = userService.uploadProfilePicture(userId, file);

        return ResponseEntity.ok(uploadedImage);
    }

    @GetMapping("/{userId}/profile-picture")
    public ResponseEntity<ImageMetadataDTO> getProfilePicture(@PathVariable String userId) {
        ImageMetadataDTO image = userService.getProfilePicture(userId);
        return ResponseEntity.ok(image);
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
    }    @PostMapping("/{userId}/programs/{programId}/enroll")
    public ResponseEntity<EnrollmentDTO> enrollProgram(
            @PathVariable String userId, 
            @PathVariable String programId,
            @RequestParam("paymentType") String paymentType,
            @RequestParam(value = "paymentProofFile", required = false) MultipartFile paymentProofFile,
            @RequestParam(value = "prerequisiteProofFile", required = false) MultipartFile prerequisiteProofFile,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "zipCode", required = false) String zipCode,
            @RequestParam(value = "notes", required = false) String notes) {
        
        try {
            EnrollmentDTO enrollmentDTO = userService.enrollProgramWithFiles(
                userId, programId, paymentType, paymentProofFile, prerequisiteProofFile,
                firstName, lastName, phone, city, country, state, street, zipCode, notes);
            return ResponseEntity.ok(enrollmentDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process enrollment: " + e.getMessage(), e);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> gelCount(){
        return ResponseEntity.ok(userService.getCountUsers());
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
