package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.Speciality;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpecialityRepository extends JpaRepository<Speciality,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Speciality e WHERE e.user.idUser = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
