package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire,Long> {
    List<Commentaire> findByKeyWordsContaining(String keyWords);
}
