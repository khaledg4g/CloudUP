package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Etat;
import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projetpi.cloudup.entities.Reservation;

import java.util.List;

public interface ReservationRepository   extends JpaRepository<Reservation,Long>  {
    List<Reservation> findReservationByEtudiantOrderByCreatedDateDesc(User user);
    List<Reservation> findReservationByProfesseurOrderByCreatedDateDesc(User user);
    List<Reservation> findReservationByStatusROrderByDateRDesc(String status);
    List<Reservation> findByProfesseurAndStatusR(User user, Etat etat);

}
