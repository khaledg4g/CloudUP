package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Forum;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.CommentaryRepository;
import com.projetpi.cloudup.repository.ForumRepository;
import com.projetpi.cloudup.repository.PublicationRepository;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.io.IOException;
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

    public Publication addPubtoForumUser(Publication pub, Long idf, Long idu) {
        Forum forum = forumRepository.findById(idf).orElse(null);
        User user = userRepository.findById(idu).orElse(null);

        if (forum != null && user != null) {
            pub.setForum(forum);
            pub.setUser(user);
            forum.setNbr_pub(forum.getNbr_pub() + 1);
            user.setNbr_pub(user.getNbr_pub() + 1);
            Publication savedPublication = publicationRepository.save(pub);
            if (savedPublication.getCommentaries() == null) {
                savedPublication.setCommentaries(new HashSet<>());
            }
            return savedPublication;
        }
        return publicationRepository.save(pub);
    }
   public void uploadImage(MultipartFile imageFile, long idpub) throws IOException {
       Publication pub = publicationRepository.findById(idpub).orElse(null);
       if (pub == null) {
           throw new EntityNotFoundException("Publication not found with ID: " + idpub);
       }
       String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
       if (fileName.contains("..")) {
           System.out.println("Invalid file name");
       }
       try {
           pub.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));
       } catch (IOException e) {
           e.printStackTrace();
       }
       publicationRepository.save(pub);
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
    public void deletePub(long idP) {
        Publication pub= publicationRepository.findById(idP).orElse(null);
        if (pub!= null) {
            pub.getForum().setNbr_pub(pub.getForum().getNbr_pub() - 1);
            publicationRepository.deleteById(idP);
        }
    }

    @Override
    public void incrementViews(Long publicationId) {
        Optional<Publication> publicationOptional = publicationRepository.findById(publicationId);
        if (publicationOptional.isPresent()) {
            Publication publication = publicationOptional.get();
            publication.setNbr_vue(publication.getNbr_vue() + 1);
            publicationRepository.save(publication);

        }

    }

    @Override
    public void markSolutionAndClosePublication() {
        try {
            List<Forum> forums = forumRepository.findAll();

            for (Forum forum : forums) {
                for (Publication publication : forum.getPublications()) {
                    if (Objects.equals(publication.getClosed(), "false")) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
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
    @Override
    public List<Commentary> retrieveAllCByPub(int idpub) {
        Publication publication = publicationRepository.findById((long) idpub).orElse(null);
        if (publication != null) {
            return commentaryRepository.findAllByPublication(publication);
        }
        return Collections.emptyList();
    }

    public Publication findById(int idpub) {
        return publicationRepository.findById((long) idpub).orElse(null);
    }

    @Override
    public List<Publication> fetchPubByIDUser(long idUser) {
        List<Publication> resultat = new ArrayList<>();
        List<Publication> publiste = publicationRepository.findAll();
        for (Publication p : publiste){
            if (p.getUser().getIdUser()== idUser){
                resultat.add(p);
            }
        }
        return resultat;
    }
}


