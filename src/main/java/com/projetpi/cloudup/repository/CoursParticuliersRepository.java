package com.projetpi.cloudup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projetpi.cloudup.entities.CoursParticuliers;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CoursParticuliersRepository extends JpaRepository<CoursParticuliers,Long>, JpaSpecificationExecutor<CoursParticuliers> {
    Page<CoursParticuliers> findByNomCoursContainingIgnoreCaseAndProfesseur_IdUser(String name, Pageable pageable, long professorId);
    @Query("SELECT c, COUNT(r.idR) as reservationCount FROM CoursParticuliers c LEFT JOIN Reservation r ON c.idCours = r.cours.idCours GROUP BY c.idCours ORDER BY reservationCount DESC")
    List<CoursParticuliers> findCoursWithMostReservations();

}
