package com.projetpi.cloudup.repository;

import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.PublicationDTO;
import com.projetpi.cloudup.entities.categories;
import org.springframework.core.metrics.StartupStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication,Long> {

    List<Publication> findBytagsContaining(String tags);

    List<Publication> findBySubjectContaining(String subject);

    List<Publication> findByContentContaining(String content);

    List<Publication> findByidpub(int idpub);

    List<Publication> findByCategoriesContaining(categories category);
    


}
