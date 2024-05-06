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
import org.springframework.stereotype.Service;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@AllArgsConstructor
@Service
public class SmsService {

    private TwilioConfig twilioConfig;
    private UserDetailsServiceImpl userDetailsServiceImpl;
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
    public OtpResponseDto sendReservationCancelationSMS(ReservationResponse reservationResponse) {
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

            User user = userRepository.findUserByIdUser(id_etudiant);
            User professor = userRepository.findUserByIdUser(id_professeur);

            PhoneNumber toStudent = new PhoneNumber(user.getPhoneNumber());
            PhoneNumber toProf = new PhoneNumber(professor.getPhoneNumber());

            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String messageContentForProf = String.format("Hello %s, your reservation with Student %s for the course '%s' on %s has been canceled successfully.",
                    reservationResponse.getProfesseurName(),
                    reservationResponse.getEtudiantName(),
                    reservationResponse.getNomcours(),
                    reservationResponse.getDateR());

            String messageContentForStudent = String.format("Hello %s, your reservation with Professor %s for the course '%s' on %s has been canceled successfully.",
                    reservationResponse.getEtudiantName(),
                    reservationResponse.getProfesseurName(),
                    reservationResponse.getNomcours(),
                    reservationResponse.getDateR());

            Message message = Message.creator(toProf, from, messageContentForProf).create();
            Message message1 = Message.creator(toStudent, from, messageContentForStudent).create();
            new OtpResponseDto(OtpStatus.DELIVERED, messageContentForStudent);
            return new OtpResponseDto(OtpStatus.DELIVERED, messageContentForProf);
        } catch (Exception e) {
            e.printStackTrace();
            return new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
    }


}






