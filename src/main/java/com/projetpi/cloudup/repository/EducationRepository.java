package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Education;
import com.projetpi.cloudup.entities.Evenement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EducationRepository extends JpaRepository<Education,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Education e WHERE e.user.idUser = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}
