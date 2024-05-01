package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvenementRepository extends JpaRepository<Evenement,Long> {
    @Query("SELECT e FROM Evenement e ORDER BY SIZE(e.participants) DESC")
    List<Evenement> findTopEventsOrderByParticipantCountDesc();
    @Query("SELECT e FROM Evenement e LEFT JOIN FETCH e.participants")
    List<Evenement> findAllByFetchParticipants();

    @Query("SELECT e FROM Evenement e WHERE e.reports > ?1")
    List<Evenement> findAllByReportsGreaterThan(int reportThreshold);
}
