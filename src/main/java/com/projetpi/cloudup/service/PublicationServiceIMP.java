package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Forum;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.CommentaryRepository;
import com.projetpi.cloudup.repository.ForumRepository;
import com.projetpi.cloudup.repository.PublicationRepository;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@NoArgsConstructor
public class PublicationServiceIMP implements IPublication {
    @Autowired

    public PublicationRepository publicationRepository;
    @Autowired
    public ForumRepository forumRepository;

    @Autowired
    public CommentaryRepository commentaryRepository;

    @Autowired
    public UserRepository userRepository;

    @Override
    public Publication addPubtoForumUser(Publication pub, Long idf, Long idu) {
        Forum forum = forumRepository.findById(idf).orElse(null);
        User user= userRepository.findById(idu).orElse(null);
        if (forum != null && user !=null) {
            pub.setForum(forum);
            pub.setUser(user);
            forum.setNbr_pub(forum.getNbr_pub() + 1);
            user.setNbr_pub(user.getNbr_pub()+1);
            Publication savedPublication = publicationRepository.save(pub);
            if (savedPublication.getCommentaries() == null)
                savedPublication.setCommentaries(new HashSet<>());
            return savedPublication;
        }
        return publicationRepository.save(pub);
    }

    @Override
    public Publication updatePub(Publication pub) {
        Optional<Publication> existingPubOptional = publicationRepository.findById((long) pub.getIdpub());
        if (existingPubOptional.isPresent()) {
            Publication existingPub = existingPubOptional.get();
            existingPub.setContent(pub.getContent());
            existingPub.setTags(pub.getTags());

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

    @Override
    @Transactional
    public void markSolutionAndClosePublication() {
        List<Forum> forums = forumRepository.findAll();

        for (Forum forum : forums) {
            for (Publication publication : forum.getPublications()) {
                if (publication.getClosed()==null){
                    Commentary solutionComment = getCommentary(publication);

                    if (solutionComment != null) {
                        solutionComment.setSolution("true");
                        publication.setClosed("true");
                        commentaryRepository.save(solutionComment);
                        publicationRepository.save(publication);
                    }
                }
            }
        }
    }

    private Commentary getCommentary(Publication publication) {
        Set<Commentary> commentaries = publication.getCommentaries();
        Commentary solutionComment = null;
        int maxDifference = 0;

        for (Commentary commentary : commentaries) {
            int difference = commentary.getVotePositif() - commentary.getVoteNegatif();
            if (difference >= 10 && difference > maxDifference) {
                maxDifference = difference;
                solutionComment = commentary;
            }
        }
        return solutionComment;
    }
}


