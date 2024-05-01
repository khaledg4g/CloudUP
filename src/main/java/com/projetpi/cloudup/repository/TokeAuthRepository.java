package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.TokenAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokeAuthRepository extends JpaRepository<TokenAuth, Long> {


    @Query(value = """
      select t from TokenAuth t inner join User u\s
      on t.user.idUser = u.idUser\s
      where u.idUser = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<TokenAuth> findAllValidTokensByUser(@Param("id") Long userId);
    Optional<TokenAuth> findByToken(String token);

}
