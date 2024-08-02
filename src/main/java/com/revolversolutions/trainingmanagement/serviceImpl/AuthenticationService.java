package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.AuthenticationResponse;
import com.revolversolutions.trainingmanagement.dto.LoginUserDto;
import com.revolversolutions.trainingmanagement.dto.RegisterUserDto;
import com.revolversolutions.trainingmanagement.entity.Token;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.enums.UserRole;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.repository.TokenRepository;
import com.revolversolutions.trainingmanagement.repository.UserRepository;
import com.revolversolutions.trainingmanagement.security.JwtService;
import com.revolversolutions.trainingmanagement.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;


    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager,
                                 EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public AuthenticationResponse register(RegisterUserDto request) {

        // check if user already exist. if exist than authenticate the user
        if(userRepository.findUserByUserName(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, null,"Username already taken");
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthenticationResponse(null, null,"Email already used");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setUserRole(UserRole.TRAINEE);

        user = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        // Send signup email
        String templatePath = "templates/emailTemplate.html";
        String subject = "Welcome to Our Application!";
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

        log.info("User registration was successful");
        return new AuthenticationResponse(accessToken, refreshToken,"User registration was successful");

    }


    public AuthenticationResponse authenticate(LoginUserDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getIdentifier(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getIdentifier())
                .orElseGet(() -> userRepository.findUserByUserName(request.getIdentifier()).orElseThrow());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");

    }

    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // extract username from token
        String email = jwtService.extractEmail(token);

        // check if the user exist in database
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("No user found"));

        // check if the token is valid
        if(jwtService.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }


    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }


}
