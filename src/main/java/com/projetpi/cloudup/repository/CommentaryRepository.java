package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary,Long> {

    List<Commentary> findByPublicationIdpubAndTagsContaining (Long idpub, String tags);


    List<Commentary> findAllByPublication(Publication publication);

    List<Commentary> findByPublicationIdpubAndContentContaining(Long idpub, String content);
}
