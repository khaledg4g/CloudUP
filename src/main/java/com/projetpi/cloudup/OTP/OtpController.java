//package com.projetpi.cloudup.OTP;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//@Slf4j
//public class OtpController {
//
//    @Autowired
//    private SmsService smsService;
//
//    @PostMapping("/sendUpdateConfirmationSMS")
//    public String sendUpdateConfirmationSMS(Authentication authentication) {
//        return smsService.sendUpdateConfirmationSMS(authentication);
//    }
//
//    @PostMapping("/validateOtp")
//    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
//        return smsService.validateOtp(otpValidationRequest);
//    }
//
//    }
