package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.repository.ForumRepository;
import com.projetpi.cloudup.repository.PublicationRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
