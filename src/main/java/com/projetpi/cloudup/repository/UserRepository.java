package com.projetpi.cloudup.repository;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);

    User findUserByIdUser(Long id);





}
