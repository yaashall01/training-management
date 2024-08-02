package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.dto.MailBody;
import com.revolversolutions.trainingmanagement.entity.ForgotPassword;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.enums.ChangePassword;
import com.revolversolutions.trainingmanagement.repository.ForgotPasswordRepository;
import com.revolversolutions.trainingmanagement.repository.UserRepository;
import com.revolversolutions.trainingmanagement.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("forgot-password")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/verify-email/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));


        int otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This the OTP for your forgot password request " + otp)
                .subject("OTP - Forgot Password Request")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(user)
                .build();

        emailService.sendSimpleEmail(mailBody);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for verification");
    }


    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email : " + email));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>( "OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verified !");
    }


    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePasswordHandler(@PathVariable String email, @RequestBody ChangePassword changePassword){
            if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
                return new ResponseEntity<>("Please enter password again !", HttpStatus.EXPECTATION_FAILED);
            }
            String encodedPassword = passwordEncoder.encode(changePassword.password());
            userRepository.updatePassword(email, encodedPassword);
            return ResponseEntity.ok("Password has been changed ");
    }


    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
