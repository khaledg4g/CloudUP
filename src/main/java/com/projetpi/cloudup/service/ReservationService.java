package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.repository.CoursParticuliersRepository;
import com.projetpi.cloudup.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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


    public Long save(ReservationRequest request, Long idCours, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        if (user.getRoles() == Role.Professor) {
            throw new EntityNotFoundException("No permission for professor to reservation");
        }
        CoursParticuliers cours = coursParticuliersRepository.findById(idCours)
                .orElseThrow(() -> new EntityNotFoundException("NO COURSE FOUND WITH ID ::" + idCours));

        Reservation savedReservation = reservationMapper.toReservation(request);

        savedReservation.setEtudiant(user);
        savedReservation.setProfesseur(cours.getProfesseur());
        savedReservation.setCours(cours);

        return reservationRepository.save(savedReservation).getIdR();
    }

    /*
        public Long updateReservaton(ReservationRequest request, Long idReservation, Authentication connectedUser) {

            Reservation updatedReservation = reservationRepository.findById(idReservation)
                    .orElseThrow(() -> new EntityNotFoundException("NO Reservation FOUND WITH ID ::" + idReservation));
            User user = (User) connectedUser.getPrincipal();
            if (!Objects.equals(updatedReservation.getProfesseur().getIdUser(), user.getIdUser())) {
                return null;
            }

            updatedReservation.setDateR(request.date());
            updatedReservation.setStatusR(request.statusR());

            return reservationRepository.save(updatedReservation).getIdR();
        }

     */
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

    public ReservationResponse getReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .map(reservationMapper::toReservationResponse)
                .orElseThrow(() -> new EntityNotFoundException(" No Reservation found with the ID ::" + idReservation));

    }

    public List<User> getMyStudents(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<Reservation> listR = reservationRepository.findByProfesseurAndStatusR(user, Etat.Confirmed);
        List<User> listUsers = listR.stream().map(Reservation::getEtudiant).toList();
        return listUsers;}
     /* List<User> list = new ArrayList<>();
        for (int i = 0; i < listUsers.size(); i++) {
            User u = User.builder()
                    .nom(listUsers.get(i).getNom())
                    .email(listUsers.get(i).getEmail())
                    .prenom(listUsers.get(i).getPrenom())
                    .phoneNumber(listUsers.get(i).getPhoneNumber())
                    .createdDate(listUsers.get(i).getCreatedDate())
                    .build();
            list.add(u);
        }
        return list;

        /* return listUsers;
    }*/
/*
public List<User> getMyStudents(Authentication connectedUser) {
    User user = (User) connectedUser.getPrincipal();
    List<Reservation> reservations = reservationRepository.findByProfesseurAndStatusR(user, Etat.Confirmed);
    return reservations.stream()
            .map(Reservation::getEtudiant)
            .map(this::transformUser)
            .collect(Collectors.toList());
}

    private User transformUser(User user) {
        return User.builder()
                .nom(user.getNom())
                .email(user.getEmail())
                .prenom(user.getPrenom())
                .phoneNumber(user.getPhoneNumber())
                .createdDate(user.getCreatedDate())
                .roles((user.getRoles()))
                .build();
    }*/
}
