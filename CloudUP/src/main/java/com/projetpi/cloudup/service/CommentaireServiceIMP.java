package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentaire;
import com.projetpi.cloudup.repository.CommentaireRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class CommentaireServiceIMP implements ICommentaire {
    public CommentaireRepository commentaireRepository;
    @Override
    public Commentaire addC(Commentaire com) {
        return commentaireRepository.save(com);
    }

    @Override
    public Commentaire updateC(Commentaire com) {
        Optional<Commentaire> existingComOptional= commentaireRepository.findById((long) com.getId_com());
        if (existingComOptional.isPresent()){
            Commentaire comexisting= existingComOptional.get();
            comexisting.setContenuC(com.getContenuC());
            return comexisting;
        } else {return null;}
    }

    @Override
    public void deleteC(int idC) {
        commentaireRepository.deleteById((long) idC);
    }

    @Override
    public List<Commentaire> retrieveAllC() {
        return commentaireRepository.findAll();
    }

    @Override
    public List<Commentaire> retrieveByKeyWords(String keyWords) {
        return commentaireRepository.findByKeyWordsContaining(keyWords);
    }
}
