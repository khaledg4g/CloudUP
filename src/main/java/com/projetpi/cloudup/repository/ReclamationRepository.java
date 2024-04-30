package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Reclamation;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation,Integer>{
    List<Reclamation> findByObjetContaining(String objet);

    List<Reclamation> findAllByOrderByTraiteDesc();
}
