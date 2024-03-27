package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary,Long> {
    List<Commentary> findBytagsContaining(String tags);

    List<Commentary> findByContentContaining(String content) ;

}
