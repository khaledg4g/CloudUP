package tn.esprit.pi.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Forum;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.ForumRepository;
import tn.esprit.pi.repositories.PublicationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class ForumServiceIMP implements IForum {
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public List<Publication> retrieveAll() {
        return publicationRepository.findAll();
    }

    @Override
    public List<Publication> retrieveByKeyWords(String keyWords) {
        return publicationRepository.findByKeyWordsContaining(keyWords);
    }
}
