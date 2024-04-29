package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollaborationRepository extends JpaRepository<Collaboration,Integer> {
    List<Collaboration> findByNomcolContaining(String objet);

}
