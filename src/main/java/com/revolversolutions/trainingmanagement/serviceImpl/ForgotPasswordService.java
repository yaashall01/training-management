package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.MailBody;
import com.revolversolutions.trainingmanagement.dto.OTPResponse;
import com.revolversolutions.trainingmanagement.entity.ForgotPassword;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.enums.ChangePassword;
import com.revolversolutions.trainingmanagement.exception.OTPException;
import com.revolversolutions.trainingmanagement.repository.ForgotPasswordRepository;
import com.revolversolutions.trainingmanagement.repository.UserRepository;
import com.revolversolutions.trainingmanagement.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ForgotPasswordService(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public OTPResponse verifyEmail(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByUser(user).orElse(null);
        if (fp != null && fp.getExpirationTime().after(Date.from(Instant.now()))){
            throw new OTPException("OTP is still valid, check ur mailbox please");
        }

        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot password request: " + otp)
                .subject("OTP - Forgot Password Request")
                .build();
        if (fp == null) {
            fp = ForgotPassword.builder()
                    .otp(otp)
                    .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                    .user(user)
                    .build();
        } else {
            fp.setOtp(otp);
            fp.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000));
        }

        emailService.sendSimpleEmail(mailBody);
        forgotPasswordRepository.save(fp);

        return new OTPResponse("Email sent for verification", email);
    }

    public void verifyOtp(Integer otp, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new OTPException("Invalid OTP for email: " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            throw new OTPException("OTP has expired!");
        }
    }

    public void changePassword(String email, ChangePassword changePassword) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            throw new OTPException("Passwords do not match!");
        }
        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email, encodedPassword);
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
