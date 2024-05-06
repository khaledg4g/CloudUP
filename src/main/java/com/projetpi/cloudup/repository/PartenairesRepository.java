package com.projetpi.cloudup.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projetpi.cloudup.entities.Partenaires;


public interface PartenairesRepository extends JpaRepository<Partenaires, Integer> {

    Partenaires findByNom(String nom);
}
