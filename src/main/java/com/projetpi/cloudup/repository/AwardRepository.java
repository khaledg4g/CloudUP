package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Award;
import com.projetpi.cloudup.entities.Evenement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AwardRepository extends JpaRepository<Award,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Award e WHERE e.user.idUser = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
