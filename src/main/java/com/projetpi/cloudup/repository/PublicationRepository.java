package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication,Long> {

    List<Publication> findByKeyWordsContaining(String keyWords);

    List<Publication> findBySubjectContaining(String subject);

    List<Publication> findByContentContaining(String content);
}
