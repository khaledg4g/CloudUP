package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum,Long> {
}
