package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Forum;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.repository.ForumRepository;
import com.projetpi.cloudup.repository.PublicationRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Service
@NoArgsConstructor
public class ForumServiceIMP implements IForum {
    private ForumRepository forumRepository;
    @Autowired
private PublicationRepository publicationRepository;

    @Autowired
    public ForumServiceIMP(PublicationRepository publicationRepository, ForumRepository forumRepository) {
        this.publicationRepository = publicationRepository;
        this.forumRepository = forumRepository;
    }


    @Override
    public Forum addForum(Forum f) {
        return forumRepository.save(f);
    }

    @Override
    public void deleteForum(Long idf) {
forumRepository.deleteById(idf);
    }

    @Override
    public List<Publication> retrieveAll() {
        return publicationRepository.findAll();
    }

    @Override
    public List<Publication> retrieveByKeyWords(@RequestParam String keyWords
                                                ) {

            return publicationRepository.findByKeyWordsContaining(keyWords);

    }
  @Override
    public List<Publication> retrieveBySubject (@RequestParam String subject){
        return publicationRepository.findBySubjectContaining(subject);
    }

    @Override
    public List<Publication> retrieveByContent (@RequestParam String content) {
        return publicationRepository.findByContentContaining(content);
    }
}
