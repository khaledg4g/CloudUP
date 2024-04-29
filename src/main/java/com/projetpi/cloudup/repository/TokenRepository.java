package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token , Long> {
    Optional<Token> findTokenByToken(String token);
}
