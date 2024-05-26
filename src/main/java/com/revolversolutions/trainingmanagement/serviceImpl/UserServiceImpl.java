package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.UserRequest;
import com.revolversolutions.trainingmanagement.dto.UserResponse;
import com.revolversolutions.trainingmanagement.entity.Enrollment;
import com.revolversolutions.trainingmanagement.entity.EnrollmentId;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import com.revolversolutions.trainingmanagement.enums.UserRole;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.EnrollmentDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.UserRequestDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.UserResponseDTOMapper;
import com.revolversolutions.trainingmanagement.repository.EnrollmentRepository;
import com.revolversolutions.trainingmanagement.repository.TrainingProgramRepository;
import com.revolversolutions.trainingmanagement.repository.UserRepository;
import com.revolversolutions.trainingmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService  {

    private final UserRepository userRepository;
    private final UserResponseDTOMapper userResponseDTOMapper;
    private final UserRequestDTOMapper userRequestDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentDTOMapper enrollmentDTOMapper;
    private final TrainingProgramRepository trainingProgramRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserResponseDTOMapper userResponseDTOMapper,
                           UserRequestDTOMapper userRequestDTOMapper,
                           PasswordEncoder passwordEncoder,
                           EnrollmentRepository enrollmentRepository,
                           EnrollmentDTOMapper enrollmentDTOMapper, TrainingProgramRepository trainingProgramRepository) {
        this.userRepository = userRepository;
        this.userResponseDTOMapper = userResponseDTOMapper;
        this.userRequestDTOMapper = userRequestDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentDTOMapper = enrollmentDTOMapper;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Fetching all users");
        Page<User> users = userRepository.findAll(pageable);
        log.info("Fetched all users");
        return users.map(userResponseDTOMapper::toDto);
    }

    @Override
    public UserResponse getUserById(long userId) {
        return userRepository.findById(userId)
                .map(userResponseDTOMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        getUserByEmailOrPhone(userRequest.getEmail(), userRequest.getPhone());
        User user = userRequestDTOMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User newUser = userRepository.save(user);
        log.info("User created successfully name : {}", newUser.getFirstName());
        return userResponseDTOMapper.toDto(newUser);
    }

    @Override
    public UserResponse updateUser(long userId, UserRequest userRequest)
            throws IOException {
        User user = findUserById(userId);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        user.setGender(userRequest.getGender());
        user.setDob(userRequest.getDob());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        if (userRequest.getProfileImage() != null && !userRequest.getProfileImage().isEmpty()) {
            user.setProfileImage(userRequest.getProfileImage().getBytes());
        }
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully name : {}", updatedUser.getFirstName());
        return userResponseDTOMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.info("User with id : {} deleted successfully", userId);
    }

    @Override
    public UserResponse addTrainer(UserRequest userRequest) {
        getUserByEmailOrPhone(userRequest.getEmail(), userRequest.getPhone());
        User user = userRequestDTOMapper.toEntity(userRequest);
        user.setUserRole(UserRole.TRAINER);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User newUser = userRepository.save(user);
        log.info("User created successfully name : {}", newUser.getFirstName());
        return userResponseDTOMapper.toDto(newUser);
    }

    @Override
    public UserResponse addTrainee(UserRequest userRequest) {
        getUserByEmailOrPhone(userRequest.getEmail(), userRequest.getPhone());
        User user = userRequestDTOMapper.toEntity(userRequest);
        user.setUserRole(UserRole.TRAINEE);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User newUser = userRepository.save(user);
        log.info("User created successfully name : {}", newUser.getFirstName());
        return userResponseDTOMapper.toDto(newUser);
    }

    @Override
    public UserResponse addAdmin(UserRequest userRequest) {
        getUserByEmailOrPhone(userRequest.getEmail(), userRequest.getPhone());
        User user = userRequestDTOMapper.toEntity(userRequest);
        user.setUserRole(UserRole.ADMIN);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User newUser = userRepository.save(user);
        log.info("User created successfully name : {}", newUser.getFirstName());
        return userResponseDTOMapper.toDto(newUser);
    }

    @Override
    public void uploadProfileImage(Long userId, MultipartFile file)
            throws IOException {
        User user = findUserById(userId);
        user.setProfileImage(file.getBytes());
        userRepository.save(user);
    }

    @Override
    public byte[] getUserProfileImage(Long userId) {
        User user = findUserById(userId);
        return user.getProfileImage();
    }

    @Override
    public EnrollmentDTO enrollProgram(Long userId, Long programId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TrainingProgram program = trainingProgramRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        Enrollment enrollment = new Enrollment();
        EnrollmentId enrollmentId = new EnrollmentId();

        enrollmentId.setUserId(userId);
        enrollmentId.setProgramId(programId);

        enrollment.setEnrollmentId(enrollmentId);
        enrollment.setUser(user);
        enrollment.setProgram(program);
        enrollment.setStatus(EnrolmentStatus.ENROLLED);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        log.info("User enrolled successfully to program : {}", program.getTitle());

        return enrollmentDTOMapper.toDto(savedEnrollment);
    }


    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
    }

    private void getUserByEmailOrPhone(String email, String phone) {
        userRepository.findUserByEmailOrPhone(email, phone)
                .ifPresent(existingUser -> {
                    throw new ResourceNotFoundException("User already exists with this email or phone number");
                });
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));
    }
}

