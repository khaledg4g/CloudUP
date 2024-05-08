package com.projetpi.cloudup.repository;
import com.projetpi.cloudup.repository.UserRepository;
import com.projetpi.cloudup.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projetpi.cloudup.entities.ReservationCollaboration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface ReservationCollaborationRepository extends JpaRepository<ReservationCollaboration, Long>{
    @Query("SELECT r FROM ReservationCollaboration r WHERE r.user.idUser = :userId")
    List<ReservationCollaboration> findByUserId(@Param("userId") Long userId);
}
