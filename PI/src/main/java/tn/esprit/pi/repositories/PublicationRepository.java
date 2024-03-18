package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Publication;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication,Long> {
    List<Publication> findByKeyWordsContaining(String keyWords);
}
