package com.projetpi.cloudup.service;

import com.projetpi.cloudup.email.payment_request.EmailServerPayment;
import com.projetpi.cloudup.email.payment_request.EmailTemplateName;
import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.repository.CoursParticuliersRepository;
import com.projetpi.cloudup.repository.ReservationRepository;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    public final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CoursParticuliersRepository coursParticuliersRepository;
    private final EmailServerPayment emailServerPayment;
    private final UserRepository userRepository;
    @Value("${application.security.mailing.frontend.payment-url}")
    private String payment_url;


    public Long save(ReservationRequest request, Long idCours, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        if (user.getRoles() == Role.Professor) {
            throw new EntityNotFoundException("No permission for professor to reservation");
        }
        CoursParticuliers cours = coursParticuliersRepository.findById(idCours)
                .orElseThrow(() -> new EntityNotFoundException("NO COURSE FOUND WITH ID ::" + idCours));
        if (user.getIdUser()==cours.getProfesseur().getIdUser()){
            throw new EntityNotFoundException("No permission for you to make a reservation");
        }
        Reservation savedReservation = reservationMapper.toReservation(request);

        savedReservation.setEtudiant(user);
        savedReservation.setProfesseur(cours.getProfesseur());
        savedReservation.setCours(cours);

        return reservationRepository.save(savedReservation).getIdR();
    }


    public Long updateReservatonStatus(String etat, Long idReservation, Authentication connectedUser) {
        Reservation updatedReservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("NO Reservation FOUND WITH ID ::" + idReservation));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(updatedReservation.getProfesseur().getIdUser(), user.getIdUser())) {
            updatedReservation.setStatusR(Etat.valueOf(etat));
            return reservationRepository.save(updatedReservation).getIdR();
        } else if (!Objects.equals(updatedReservation.getEtudiant().getIdUser(), user.getIdUser())) {
            updatedReservation.setStatusR(Etat.valueOf(etat));
            return reservationRepository.save(updatedReservation).getIdR();
        } else return null;


    }

    public Long updateReservationDate(Date dateR, Long idReservation, Authentication connectedUser) {
        Reservation updatedReservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("NO Reservation FOUND WITH ID ::" + idReservation));
        User user = (User) connectedUser.getPrincipal();
        if (updatedReservation.getEtudiant().getIdUser() == user.getIdUser()) {
            updatedReservation.setDateR(dateR);
        }
        if (!Objects.equals(updatedReservation.getEtudiant().getIdUser(), user.getIdUser())) {
            throw new EntityNotFoundException("NO PERMISSION TO Edit Reservation ::" + idReservation);
        }
        return reservationRepository.save(updatedReservation).getIdR();

    }


    public void deleteReservation(Long idReservation, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("No Course found"));
        if (!Objects.equals(reservation.getProfesseur().getIdUser(), user.getIdUser())) {
            throw new EntityNotFoundException("NO PERMISSION TO DELETE Reservation ::" + idReservation);
        }
        if (!Objects.equals(reservation.getEtudiant().getIdUser(), user.getIdUser())) {
            throw new EntityNotFoundException("NO PERMISSION TO DELETE Reservation ::" + idReservation);
        }
        reservationRepository.deleteById(idReservation);

    }

    public List<ReservationResponse> getReservationByOwnerStudent(Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        List<Reservation> listR = reservationRepository.findReservationByEtudiantOrderByCreatedDateDesc(user);
        List<ReservationResponse> listRResponse = listR.stream().map(reservationMapper::toReservationResponse).toList();
        return listRResponse;

    }

    public List<ReservationResponse> getReservationByOwnerProfeesor(Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        List<Reservation> listR = reservationRepository.findReservationByProfesseurOrderByCreatedDateDesc(user);
        List<ReservationResponse> listRResponse = listR.stream().map(reservationMapper::toReservationResponse).toList();
        return listRResponse;


    }

    public List<ReservationResponse> getReservationStatus(String status, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<Reservation> listR = reservationRepository.findReservationByStatusROrderByDateRDesc(status);
        List<ReservationResponse> listRResponse = listR.stream().map(reservationMapper::toReservationResponse).toList();
        return listRResponse;
    }

    public Reservation getReservationById(Long idReservation) {
        return reservationRepository.findReservationByIdR(idReservation);

    }

    public List<User> getMyStudents(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<Reservation> listR = reservationRepository.findByProfesseurAndStatusR(user, Etat.Confirmed);
        List<User> listUsers = listR.stream().map(Reservation::getEtudiant).toList();
        return listUsers;
    }

    public void sendPaymentEmail(Long idR) throws MessagingException {
        Reservation R = reservationRepository.findReservationByIdR(idR);
        if (R == null) {
            throw new IllegalStateException("Reservation not found!");
        }
        User user = userRepository.findUserByIdUser(R.getEtudiant().getIdUser());
        if (user == null) {
            throw new IllegalStateException("User not found!");
        }

        // Assume your application is hosted at this domain and the checkout route is defined as shown
        String domainUrl = "http://localhost:4200/Home/students";
        String paymentUrl = String.format("%s/checkout;reservationId=%d", domainUrl, idR);

        emailServerPayment.sendMail(
                user.getEmail(),
                user.fullName(), // assuming there is a method getFullName in User class
                EmailTemplateName.PAYMENT_REQUEST,
                idR, // passing reservation ID as string
                "PAYMENT REQUEST",
                domainUrl);
    }
    /*
    public void sendPaymentEmail(Long  idR) throws MessagingException {
        Reservation R = reservationRepository.findReservationByIdR(idR);
        User user = userRepository.findUserByIdUser(R.getEtudiant().getIdUser());

        emailServerPayment.sendMail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.PAYMENT_REQUEST,
                payment_url,
                "PAYMENT REQUEST");

    }*/
/*
    public ReservationResponse createReservationResponse(Reservation reservation) {
        User professor = reservation.getProfesseur();
        User student = reservation.getEtudiant();

        String profPhone = professor != null ? professor.getPhoneNumber() : null;
        String studentPhone = student != null ? student.getPhoneNumber() : null;

        return ReservationResponse.builder()
                .idR(reservation.getIdR())
                .dateR(reservation.getDateR())
                .statusR(reservation.getStatusR())
                .id_etudiant(student != null ? student.getIdUser() : null)
                .id_professeur(professor != null ? professor.getIdUser() : null)
                .id_cours(reservation.getCours() != null ? reservation.getCours().getIdCours() : null)
                .etudiantName(student != null ? student.getName() : null)
                .professeurName(professor != null ? professor.getName() : null)
                .nomcours(reservation.getCours() != null ? reservation.getCours().getNomCours() : null)
                .professeurPhoneNumber(profPhone)
                .etudiantPhoneNumber(studentPhone)
                .build();
    }
*/

}
