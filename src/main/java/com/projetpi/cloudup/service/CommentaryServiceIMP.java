package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.CommentaryRepository;
import com.projetpi.cloudup.repository.PublicationRepository;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@NoArgsConstructor
public class CommentaryServiceIMP implements ICommentary {
    @Autowired
    @Lazy
    private CommentaryRepository commentaryRepository;
    @Autowired
private PublicationRepository publicationRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CommentaryServiceIMP(CommentaryRepository commentaryRepository, PublicationRepository publicationRepository) {
        this.commentaryRepository = commentaryRepository;
        this.publicationRepository = publicationRepository;
    }

    @Override
    @Transactional
    public Commentary addCommentToPubUser (Commentary com, Long idpub, Long idu) {

        Publication publication = publicationRepository.findById(idpub).orElse(null);
        User user= userRepository.findById(idu).orElse(null);
        if ((publication != null && user != null) && (!publication.getClosed().equals("true"))) {
            com.setPublication(publication);
            com.setUser(user);
            publication.setNbr_com(publication.getNbr_com() + 1);
            user.setNbr_com(user.getNbr_com()+1);
            return commentaryRepository.save(com);
        }
        return null;
    }

    @Override
    public Commentary updateC(Commentary com) {
        Optional<Commentary> existingComOptional= commentaryRepository.findById((long) com.getIdCom());
        if (existingComOptional.isPresent()){
            Commentary comexisting= existingComOptional.get();
            comexisting.setContent(com.getContent());
            comexisting.setTags(com.getTags());
            return commentaryRepository.save(comexisting);
        } else {return null;}
    }

    @Override
    public void deleteC(int idC) {

        commentaryRepository.deleteById((long) idC);
        Optional<Commentary> existingComOptional = commentaryRepository.findById((long) idC);
        if (existingComOptional.isPresent()) {
            Commentary com = existingComOptional.get();
            com.setVoteNegatif(com.getVoteNegatif() - 1);
            commentaryRepository.save(com);
        }

    }



    @Override
    public List<Commentary> retrieveByTags(String tags) {
        return commentaryRepository.findBytagsContaining(tags);
    }

    @Override
    public List<Commentary> findByContent(Long idpub, String content) {
        return commentaryRepository.findByPublicationIdpubAndContentContaining(idpub, content);
    }

    @Override
    public void upvoteCommentary(int commentId) {
        Optional<Commentary> existingComOptional = commentaryRepository.findById((long) commentId);
        if (existingComOptional.isPresent()) {
            Commentary com = existingComOptional.get();
            com.setVotePositif(com.getVotePositif() + 1);
            commentaryRepository.save(com);
        }
    }

    @Override
    public void downvoteCommentary(int commentId) {
        Optional<Commentary> existingComOptional = commentaryRepository.findById((long) commentId);
        if (existingComOptional.isPresent()) {
            Commentary com = existingComOptional.get();
            com.setVoteNegatif(com.getVoteNegatif() + 1);
            commentaryRepository.save(com);
        }
    }

    public int getCommentLikes(int idCom) {
        Commentary comment = commentaryRepository.findById((long) idCom).orElse(null);
        return comment.getVotePositif();
    }
    public int getCommentDislikes(int idCom) {
        Commentary comment = commentaryRepository.findById((long) idCom)
                .orElse(null) ;
        return comment.getVoteNegatif();
    }


}


