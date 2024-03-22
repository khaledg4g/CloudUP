package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Forum;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.ForumRepository;
import com.projetpi.cloudup.repository.PublicationRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@NoArgsConstructor
public class PublicationServiceIMP implements IPublication {
    @Autowired

    public PublicationRepository publicationRepository;
    @Autowired
    public ForumRepository forumRepository;

    @Override
    public Publication addPubtoForum(Publication pub, Long idf) {
        Forum forum = forumRepository.findById(idf).orElse(null);
        if (forum != null) {
            pub.setForum(forum);
            forum.setNbr_pub(forum.getNbr_pub() + 1);
            Publication savedPublication = publicationRepository.save(pub);
            if (savedPublication.getCommentaries() == null)
                savedPublication.setCommentaries(new HashSet<>());
            return savedPublication;
        }
        return publicationRepository.save(pub);
    }



    @Override
    public Publication updatePub(Publication pub) {
        Optional<Publication> existingPubOptional = publicationRepository.findById((long) pub.getId_pub());
        if (existingPubOptional.isPresent()) {
            Publication existingPub = existingPubOptional.get();
            existingPub.setContent(pub.getContent());
            existingPub.setKeyWords(pub.getKeyWords());

            return publicationRepository.save(existingPub);
        } else {
            return null;
        }
    }

    @Override
    public void deletePub(int idP) {
        publicationRepository.deleteById((long) idP);
    }

    @Override
    @Transactional
    public void incrementViews(Long publicationId) {
        Optional<Publication> publicationOptional = publicationRepository.findById(publicationId);
        if (publicationOptional.isPresent()) {
            Publication publication = publicationOptional.get();
            publication.setNbr_vue(publication.getNbr_vue() + 1);
            publicationRepository.save(publication);

        }

    }
}
