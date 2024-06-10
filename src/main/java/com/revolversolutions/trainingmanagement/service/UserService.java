package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.UserRequest;
import com.revolversolutions.trainingmanagement.dto.UserResponse;
import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService{
    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(String userId);


    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(String userId, UserRequest userRequest) throws IOException;

    void deleteUser(String userId);

    UserResponse addTrainer(UserRequest userRequest);

    UserResponse addTrainee(UserRequest userRequest);
    UserResponse addAdmin(UserRequest userRequest);


    User loadUserByUsername(String username) throws UsernameNotFoundException;

    FileDB getUserProfileImage(String userId);

    void uploadUserProfileImage(String userId, MultipartFile file) throws IOException;

    EnrollmentDTO enrollProgram(String userId, String programId);


    //void uploadProfileImage(String userId, MultipartFile file)
    //       throws IOException;

    //byte[] getUserProfileImage(String userId);

}
