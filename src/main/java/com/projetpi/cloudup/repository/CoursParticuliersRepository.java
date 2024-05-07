package com.projetpi.cloudup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projetpi.cloudup.entities.CoursParticuliers;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CoursParticuliersRepository extends JpaRepository<CoursParticuliers,Long>, JpaSpecificationExecutor<CoursParticuliers> {
    Page<CoursParticuliers> findByNomCoursContainingIgnoreCaseAndProfesseur_IdUser(String name, Pageable pageable, long professorId);
}
