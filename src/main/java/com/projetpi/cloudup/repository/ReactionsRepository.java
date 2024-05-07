package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.Reactions;
import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionsRepository extends JpaRepository<Reactions,Long> {
  Long countByEvenement(Evenement evenement);
  Reactions findByEvenementAndUser_IdUser(Evenement evenement , Long user);

}
