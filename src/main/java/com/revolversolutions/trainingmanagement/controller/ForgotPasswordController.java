package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.dto.OTPResponse;
import com.revolversolutions.trainingmanagement.enums.ChangePassword;
import com.revolversolutions.trainingmanagement.serviceImpl.ForgotPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("forgot-password")
@Slf4j
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/verify-email/{email}")
    public ResponseEntity<?> verifyEmail(@PathVariable String email) {
        log.info("Request received to verify email: {}", email);
        try {
            OTPResponse response = forgotPasswordService.verifyEmail(email);
            log.info("Verification email sent to: {}", email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error verifying email: {}", email, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<?> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        log.info("Request received to verify OTP for email: {}", email);
        try {
            forgotPasswordService.verifyOtp(otp, email);
            log.info("OTP verified for email: {}", email);
            return ResponseEntity.ok(new OTPResponse("OTP verified!", email));
        } catch (Exception e) {
            log.error("Error verifying OTP for email: {}", email, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<OTPResponse> changePasswordHandler(@PathVariable String email, @RequestBody ChangePassword changePassword) {
        log.info("Request received to change password for email: {}", email);
        try {
            forgotPasswordService.changePassword(email, changePassword);
            log.info("Password changed for email: {}", email);
            return ResponseEntity.ok(new OTPResponse("Password has been changed", email));
        } catch (Exception e) {
            log.error("Error changing password for email: {}", email, e);
            return new ResponseEntity<>(new OTPResponse("Error changing password for email", email), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
