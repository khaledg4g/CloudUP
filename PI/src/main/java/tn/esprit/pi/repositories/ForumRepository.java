package tn.esprit.pi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entities.Forum;
import tn.esprit.pi.entities.Publication;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum,Long> {
}
