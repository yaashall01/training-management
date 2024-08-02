package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.UserRequest;
import com.revolversolutions.trainingmanagement.dto.UserResponse;
import com.revolversolutions.trainingmanagement.entity.*;
import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import com.revolversolutions.trainingmanagement.enums.UserRole;
import com.revolversolutions.trainingmanagement.exception.AlreadyEnrolledException;
import com.revolversolutions.trainingmanagement.exception.FileStorageException;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.EnrollmentDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.UserRequestDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.UserResponseDTOMapper;
import com.revolversolutions.trainingmanagement.repository.*;
import com.revolversolutions.trainingmanagement.service.EmailService;
import com.revolversolutions.trainingmanagement.service.UserService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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
    private final EmailService emailService;
    private final FileStorageService storageService;
    private final TokenRepository tokenRepository;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserResponseDTOMapper userResponseDTOMapper,
                           UserRequestDTOMapper userRequestDTOMapper,
                           PasswordEncoder passwordEncoder,
                           EnrollmentRepository enrollmentRepository,
                           EnrollmentDTOMapper enrollmentDTOMapper,
                           TrainingProgramRepository trainingProgramRepository,
                           EmailService emailService, FileStorageService storageService, TokenRepository tokenRepository, AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.userResponseDTOMapper = userResponseDTOMapper;
        this.userRequestDTOMapper = userRequestDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentDTOMapper = enrollmentDTOMapper;
        this.trainingProgramRepository = trainingProgramRepository;
        this.emailService = emailService;
        this.storageService = storageService;
        this.tokenRepository = tokenRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Fetching all users");
        Page<User> users = userRepository.findAll(pageable);
        log.info("Fetched all users");
        return users.map(userResponseDTOMapper::toDto);
    }

    @Override
    public UserResponse getUserById(String userId) {
        return userRepository.findUserByUserId(userId)
                .map(userResponseDTOMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
    }

    @Override
    @Transactional
    public List<EnrollmentDTO> getUserEnrollments(String userId) {
        User user = findUserById(userId);
        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        log.info("User : {}, Enrollments : {}", userId, enrollments);
        return enrollmentDTOMapper.toDtos(enrollments);
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
    public UserResponse updateUser(String userId,
                                   UserRequest userRequest)
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
        //user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

/*        if (userRequest.getProfileImage() != null && !userRequest.getProfileImage().isEmpty()) {
            user.setProfileImage(userRequest.getProfileImage().getBytes());
        }

 */
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully name : {}", updatedUser.getFirstName() + " "+updatedUser.getLastName());
        return userResponseDTOMapper.toDto(updatedUser);
    }
    //TODO: make sure this delete user service cover an enrolled user or authenticated user !
    @Override
    @Transactional
    public void deleteUser(String userId) {
        User deletedUser = findUserById(userId);

//        if (deletedUser.getEnrollments() != null) {
//            enrollmentRepository.deleteAll(deletedUser.getEnrollments());
//        }
//
       if (deletedUser.getTokens() != null) {
           tokenRepository.deleteAll(deletedUser.getTokens());
        }
//
//        if (deletedUser.getSessions() != null) {
//            attendanceRepository.deleteAll(deletedUser.getSessions());
//        }

        attendanceRepository.deleteByUserId(deletedUser.getId());

        userRepository.delete(deletedUser);

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
    public FileDB getUserProfileImage(String userId){
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with userId " + userId));
        return user.getProfileImage();
    }

    @Override
    public void uploadUserProfileImage(String userId, MultipartFile file){
        if (file == null || file.isEmpty())
            throw new FileStorageException("Uploaded files list is empty or null");

        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with userId " + userId));

        try {
            FileDB fileDB = storageService.store(file);
            user.setProfileImage(fileDB);
            userRepository.save(user);
        } catch (IOException e) {
            throw new FileStorageException("Error storing file", e);
        } catch (IllegalArgumentException e) {
            throw new FileStorageException("Uploaded file is not a valid image", e);
        }
    }

    @Override
    public EnrollmentDTO enrollProgram(String userId, String programId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TrainingProgram program = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        EnrollmentId enrollmentId = new EnrollmentId(user.getId(), program.getId());

        if (enrollmentRepository.existsById(enrollmentId)) {
            throw new AlreadyEnrolledException("User is already enrolled in this program");
        }
        //TODO: Verify Program hierarchy, status, certificate ...

        Enrollment enrollment = new Enrollment();

        enrollment.setId(enrollmentId);
        enrollment.setUser(user);
        enrollment.setProgram(program);
        enrollment.setStatus(EnrolmentStatus.ENROLLED);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        log.info("User enrolled successfully to program : {}", program.getTitle());

        // Send signup email
        String templatePath = "templates/emailTemplate.html";
        String subject = "You are just enrolled "+ program.getTitle();
        String message = "Congratulations you're in now enjooy !!";
        Map<String, String> variables = Map.of(
                "subject",subject,
                "name", user.getFirstName() + " " + user.getLastName(),
                "message",message
        );
        try {
            emailService.sendEmail(user.getEmail(), subject, templatePath, variables);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }

        return enrollmentDTOMapper.toDto(savedEnrollment);
    }

    @Override
    public User findUserById(String userId) {
        return userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
    }

    private void getUserByEmailOrPhone(String email, String phone) {
        userRepository.findUserByEmailOrPhone(email, phone)
                .ifPresent(existingUser -> {
                    throw new ResourceNotFoundException("User already exists with this email or phone number");
                });
    }

    public User loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(identifier);
        if(user == null){
            user = userRepository.findByUserName(identifier);
        }
        if (user == null){
            throw new UsernameNotFoundException("User not found with email or username: " + identifier);
        }
        return user;

    }


   // return userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    //TODO: reset password service



}

