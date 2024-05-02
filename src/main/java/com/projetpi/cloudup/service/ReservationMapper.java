package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Reservation;
import com.projetpi.cloudup.entities.ReservationRequest;
import com.projetpi.cloudup.entities.ReservationResponse;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {
    public Reservation toReservation(ReservationRequest request) {
        return new Reservation().builder()
                .idR(request.idR())
                .dateR(request.dateR())
                .statusR(request.statusR())
                .build();
    }
    public ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse().builder()
                .idR(reservation.getIdR())
                .dateR(reservation.getDateR())
                .statusR(reservation.getStatusR())
                .id_cours(reservation.getCours().getIdCours())
                .id_etudiant(reservation.getEtudiant().getIdUser())
                .id_professeur(reservation.getProfesseur().getIdUser())
                .etudiantName(reservation.getEtudiant().fullName()) //added field
                .professeurName(reservation.getProfesseur().fullName())//added field
                .nomcours(reservation.getCours().getNomCours())//added field
                .build();
    }
}
