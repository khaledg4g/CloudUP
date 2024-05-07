package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);

    User findUserByIdUser(Long id);
    @Query("SELECT u, COUNT(r.idR) as confirmedReservationCount FROM User u JOIN Reservation r ON u.idUser = r.professeur.idUser WHERE r.statusR = 'Confirmed' GROUP BY u.idUser ORDER BY confirmedReservationCount DESC")
    List<User> findProfessorsWithMostConfirmedReservations();

}
