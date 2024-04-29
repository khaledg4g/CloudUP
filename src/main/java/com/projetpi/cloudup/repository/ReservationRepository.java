package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projetpi.cloudup.entities.Reservation;

import java.util.List;

public interface ReservationRepository   extends JpaRepository<Reservation,Long>  {
    List<Reservation> findReservationByEtudiant(User user);
    List<Reservation> findReservationByProfesseur(User user);
    List<Reservation> findReservationByStatusROrderByDateRDesc(String status);
}
