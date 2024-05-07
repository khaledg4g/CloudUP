package com.projetpi.cloudup.OTP;

import com.projetpi.cloudup.entities.Reservation;
import com.projetpi.cloudup.entities.ReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class OtpController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }
/*
    @PostMapping("/send-otp")
    public OtpResponseDto sendOtp( @RequestBody OtpRequest otpRequest) {
        log.info("inside sendOtp :: "+otpRequest.getUsername());
        return smsService.sendSMS( otpRequest);
    }
    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        log.info("inside validateOtp :: "+otpValidationRequest.getUsername()+" "+otpValidationRequest.getOtpNumber());
        return smsService.validateOtp(otpValidationRequest);
    }*/

    @PostMapping("/send-reservation-details")
    public OtpResponseDto sendReservationDetails(@RequestBody Long idR) {
        return smsService.sendReservationDetailsSMS(idR);
    }

    @PostMapping("/sendReservationConfirmationSMS")
    public OtpResponseDto sendReservationConfirmationSMS(@RequestBody ReservationResponse reservation) {
        return smsService.sendReservationConfirmationSMS(reservation);
    }

    @PostMapping("/sendReservationCancelationSMS")
    public OtpResponseDto sendReservationCancelationSMS(@RequestBody ReservationResponse reservation,Authentication connectedUser) {
        return smsService.sendReservationCancelationSMS(connectedUser,reservation);
    }


}
