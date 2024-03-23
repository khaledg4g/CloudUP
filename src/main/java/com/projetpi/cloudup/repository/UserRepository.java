package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
