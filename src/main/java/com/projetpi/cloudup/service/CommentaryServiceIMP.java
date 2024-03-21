package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.repository.CommentaryRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class CommentaryServiceIMP implements ICommentary {
    @Autowired
    public CommentaryRepository commentaryRepository;


    @Override
    public Commentary addC(Commentary com) {
        return commentaryRepository.save(com);
    }

    @Override
    public Commentary updateC(Commentary com) {
        Optional<Commentary> existingComOptional= commentaryRepository.findById((long) com.getId_com());
        if (existingComOptional.isPresent()){
            Commentary comexisting= existingComOptional.get();
            comexisting.setContenuC(com.getContenuC());
            return comexisting;
        } else {return null;}
    }

    @Override
    public void deleteC(int idC) {
        commentaryRepository.deleteById((long) idC);
    }

    @Override
    public List<Commentary> retrieveAllC() {
        return commentaryRepository.findAll();
    }

    @Override
    public List<Commentary> retrieveByKeyWords(String keyWords) {

            return commentaryRepository.findByKeyWords(keyWords);

    }
}
