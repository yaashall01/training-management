package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.UserRequest;
import com.revolversolutions.trainingmanagement.dto.UserResponse;
import com.revolversolutions.trainingmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService{
    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(long userId);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(long userId, UserRequest userRequest) throws IOException;

    void deleteUser(long userId);

    UserResponse addTrainer(UserRequest userRequest);

    UserResponse addTrainee(UserRequest userRequest);
    UserResponse addAdmin(UserRequest userRequest);

    void uploadProfileImage(Long userId, MultipartFile file)
            throws IOException;

    byte[] getUserProfileImage(Long userId);

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    public EnrollmentDTO enrollProgram(Long userId, Long programId);
}
