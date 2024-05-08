package com.projetpi.cloudup.OTP;

import java.util.HashMap;
import java.util.Map;

import com.projetpi.cloudup.Config.TwilioConfig;
import com.projetpi.cloudup.entities.Reservation;
import com.projetpi.cloudup.entities.ReservationResponse;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.ReservationRepository;
import com.projetpi.cloudup.repository.UserRepository;
import com.projetpi.cloudup.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@AllArgsConstructor
@Service
public class SmsService {

    private TwilioConfig twilioConfig;
    private UserRepository userRepository;
    private ReservationRepository reservationRepository;


    public OtpResponseDto sendReservationDetailsSMS(Long idR) {
        OtpResponseDto otpResponseDto = null;
        Reservation R = reservationRepository.findReservationByIdR(idR);

        PhoneNumber to = new PhoneNumber(R.getProfesseur().getPhoneNumber());

        PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
        String messageContent = String.format("Hello %s, you have a new reservation from %s for the course %s on %s .",
                R.getProfesseur().fullName(),
                R.getEtudiant().fullName(),
                R.getCours().getNomCours(),
                R.getDateR());

        Message message = Message.creator(to, from, messageContent).create();
        return new OtpResponseDto(OtpStatus.DELIVERED, messageContent);

    }

    public OtpResponseDto sendReservationConfirmationSMS(ReservationResponse reservationResponse) {
        OtpResponseDto otpResponseDto = null;
        try {
            if (reservationResponse.getProfesseurName() == null ||
                    reservationResponse.getEtudiantName() == null ||
                    reservationResponse.getNomcours() == null ||
                    reservationResponse.getDateR() == null) {
                throw new IllegalArgumentException("Required field is null");
            }

            long id_etudiant = reservationResponse.getId_etudiant();

            User user = userRepository.findUserByIdUser(id_etudiant);

            PhoneNumber to = new PhoneNumber(user.getPhoneNumber());

            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String messageContent = String.format("Hello %s, your reservation with Professor %s for the course '%s' on %s has been confirmed successfully.",
                    reservationResponse.getEtudiantName(),
                    reservationResponse.getProfesseurName(),
                    reservationResponse.getNomcours(),
                    reservationResponse.getDateR());

            Message message = Message.creator(to, from, messageContent).create();
            return new OtpResponseDto(OtpStatus.DELIVERED, messageContent);
        } catch (Exception e) {
            e.printStackTrace();
            return new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
    }
    public OtpResponseDto sendReservationCancelationSMS(Authentication connectedUser, ReservationResponse reservationResponse) {
        User userConnected = (User) connectedUser.getPrincipal();
        OtpResponseDto otpResponseDto = null;
        try {
            if (reservationResponse.getProfesseurName() == null ||
                    reservationResponse.getEtudiantName() == null ||
                    reservationResponse.getNomcours() == null ||
                    reservationResponse.getDateR() == null) {
                throw new IllegalArgumentException("Required field is null");
            }


            long id_professeur = reservationResponse.getId_professeur();

            long id_etudiant = reservationResponse.getId_etudiant();
            User student = userRepository.findUserByIdUser(id_etudiant);
            User professor = userRepository.findUserByIdUser(id_professeur);

            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            PhoneNumber toStudent = new PhoneNumber(student.getPhoneNumber());
            PhoneNumber toProf = new PhoneNumber(professor.getPhoneNumber());


            if (userConnected.getIdUser()==reservationResponse.getId_professeur()) {

                String messageContentForStudent = String.format("Hello %s, your reservation with Professor %s for the course '%s' on %s has been canceled .",
                        reservationResponse.getEtudiantName(),
                        reservationResponse.getProfesseurName(),
                        reservationResponse.getNomcours(),
                        reservationResponse.getDateR());
                Message message1 = Message.creator(toStudent, from, messageContentForStudent).create();
                return  new OtpResponseDto(OtpStatus.DELIVERED, messageContentForStudent);
            }
            else if (userConnected.getIdUser()==reservationResponse.getId_etudiant()) {

                String messageContentForProf = String.format("Hello %s, your reservation with Student %s for the course '%s' on %s has been canceled .",
                        reservationResponse.getProfesseurName(),
                        reservationResponse.getEtudiantName(),
                        reservationResponse.getNomcours(),
                        reservationResponse.getDateR());
                Message message = Message.creator(toProf, from, messageContentForProf).create();
                return new OtpResponseDto(OtpStatus.DELIVERED, messageContentForProf);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }

import com.projetpi.cloudup.Config.TwilioConfig;
import com.projetpi.cloudup.RestController.UserUpdatePWDRequest;
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

    @Autowired

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



    public String sendUpdatePasswordSMS(UserUpdatePWDRequest U) {
        User user = userRepository.findUserByPhoneNumber(U.getPhoneNumber());

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






