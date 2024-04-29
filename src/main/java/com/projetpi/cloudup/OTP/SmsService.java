package com.projetpi.cloudup.OTP;

import com.projetpi.cloudup.Config.TwilioConfig;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.UserRepository;
import com.projetpi.cloudup.service.UserDetailsServiceImpl;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Set;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class SmsService {

    Map<String, String> otpMap = new HashMap<>();
    private UserRepository userRepository;

    @Autowired
    private TwilioConfig twilioConfig;

    public String sendUpdateConfirmationSMS(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        OtpResponseDto otpResponseDto = null;
        try {
            // User user = userRepository.findUserByIdUser(U.getIdUser());
        PhoneNumber to = new PhoneNumber(user.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());

        String otp = generateOTP();
        String otpMessage = "Dear "+ user.getNom() + ", Your validation code is  " + otp ;

        Message message = Message
                .creator(to, from, otpMessage)
                .create();
            otpMap.put(user.getUsername(), otp);

            return otp;
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return null;

    }
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }



    public String validateOtp(OtpValidationRequest otpValidationRequest) {
        Set<String> keys = otpMap.keySet();
        String username = null;
        for(String key : keys)
            username = key;
        if (otpValidationRequest.getUsername().equals(username)) {
            otpMap.remove(username,otpValidationRequest.getOtpNumber());
            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
    }

}






