package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Partenaires;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartenairesRepository extends JpaRepository<Partenaires,Integer> {

    Partenaires findByNom(String nom);
}
