package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Collaboration;
import com.projetpi.cloudup.entities.Cours;
import com.projetpi.cloudup.entities.Partenaires;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.CollaborationRepository;
import com.projetpi.cloudup.repository.PartenairesRepository;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ComponentScan
@Component
@Service
public class CollaborationServiceIMP implements ICollaboration {
    @Autowired
    private CollaborationRepository collaborationRepository;
    @Autowired
    private PartenairesRepository partenairesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public CollaborationServiceIMP(CollaborationRepository collaborationRepository, PartenairesRepository partenairesRepository) {
        this.collaborationRepository = collaborationRepository;
        this.partenairesRepository = partenairesRepository;
    }

    @Override
    public String saveCollaboration(MultipartFile file, String nomcol, String desccol, Date datecol, String placecol, float prixcol, int partenaires_id_part,int nbrres,long user_id_user) {
        Collaboration c = new Collaboration();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("Invalid file name");
            return "Invalid file name";
        }
        try {
            c.setImgcol(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        c.setNomcol(nomcol);
        c.setDesccol(desccol);
        c.setDatecol(datecol);
        c.setPlacecol(placecol);
        c.setPrixcol(prixcol);
        Partenaires partenaires = partenairesRepository.findById(partenaires_id_part).orElse(null);
        c.setPartenaires(partenaires);
        c.setNbrres(nbrres);
        User user = userRepository.findById(user_id_user).orElse(null);
        c.setUser(user);


        collaborationRepository.save(c);
        return fileName;
    }







    @Override
    public Collaboration addCollaboration(Collaboration collaboration) {
        return collaborationRepository.save(collaboration);
    }

    @Override
    public Collaboration retrieveByIdCollaboration(int idcol) {
        return collaborationRepository.findById(idcol).orElse(null);
    }



    @Override
    public List<Collaboration> retriveCollaboration() {
        return collaborationRepository.findAll();
    }

    @Override
    public void deleteCollaboration(int idcol) {
        collaborationRepository.deleteById(idcol);

    }

    @Lock(value = LockModeType.WRITE)
    @Transactional
    public Collaboration updateCollaboration(Collaboration collaboration) {
        Optional<Collaboration> existingCollaborationOptional = collaborationRepository.findById(collaboration.getIdcol());
        if (existingCollaborationOptional.isPresent()) {
            Collaboration existingCollaboration = existingCollaborationOptional.get();
            updateCollaborationFields(collaboration, existingCollaboration);
            return collaborationRepository.save(existingCollaboration);
        } else {
            return null;
        }
    }

    private void updateCollaborationFields(Collaboration updatedCollaboration, Collaboration existingCollaboration) {
        for (Field field : updatedCollaboration.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object updatedValue = field.get(updatedCollaboration);
                if (updatedValue != null) {
                    field.set(existingCollaboration, updatedValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }





    @Override
    public User addCollaborationAndAssignToUser(Collaboration collaboration, int idcol) {
        return null;
    }

    @Override
    public Cours addCollaborationAndAssignToCours(Cours cours, int idcour) {
        return null;
    }

    @Override
    public List<Collaboration> findCollaborationWithSorting(String field) {
        return collaborationRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }


    public Page<Collaboration> findCollaborationWithPagination(int offset, int pageSize){
        Page<Collaboration> Collaborations = collaborationRepository.findAll(PageRequest.of(offset, pageSize));
        return  Collaborations;
    }

    @Override
    public Page<Collaboration> GetAllWithPagination(int size, int page) {
        return collaborationRepository.findAll(PageRequest.of(size,page));

    }

    @Override
    public List<Collaboration> RetrieveObjet(String objet) {
        return collaborationRepository.findByNomcolContaining(objet);
    }

    @Override
    public void upvoteCollaboration(int commentId) {

        Optional<Collaboration> existingComOptional = collaborationRepository.findById((int) commentId);
        if (existingComOptional.isPresent()) {
            Collaboration com = existingComOptional.get();
            com.setVotePositif(com.getVotePositif() + 1);
            collaborationRepository.save(com);
        }
    }

    @Override
    public void downvoteCollaboration(int commentId) {
        Optional<Collaboration> existingComOptional = collaborationRepository.findById((int) commentId);
        if (existingComOptional.isPresent()) {
            Collaboration com = existingComOptional.get();
            com.setVoteNegatif(com.getVoteNegatif() + 1);
            collaborationRepository.save(com);
        }
    }

    @Override
    public void downnumberCollaboration(int idcol) {
        Optional<Collaboration> existingComOptional = collaborationRepository.findById((int) idcol);
        if (existingComOptional.isPresent()) {
            Collaboration com = existingComOptional.get();
            com.setNbrres(com.getNbrres() - 1);
            collaborationRepository.save(com);
        }
    }

    @Override
    public int getCollaborationLikes(int idcol) {
        Collaboration collaboration = collaborationRepository.findById((int) idcol).orElse(null);
        return collaboration.getVotePositif();
    }

    @Override
    public int getCollaborationDislikes(int idcol) {
        Collaboration collaboration = collaborationRepository.findById((int) idcol)
                .orElse(null) ;
        return collaboration.getVotePositif();
    }

    public Collaboration getCollaborationFromDatabase(int idcol) {
        // Retrieve collaboration from the repository based on the collaboration ID
        return collaborationRepository.findById(idcol)
                .orElse(null); // Return null if collaboration is not found
    }

}
