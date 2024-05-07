package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.Reactions;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.EvenementRepository;
import com.projetpi.cloudup.repository.ReactionsRepository;
import com.projetpi.cloudup.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReactionServiceIMP implements IReaction {
    @Autowired
    ReactionsRepository reactionRepository;
    @Autowired
    EvenementRepository evenementRepository;
    @Autowired
    UtilisateurRepository userRespository;
    @Override
    public List<Reactions> retrieveAllReactions() {
        return reactionRepository.findAll();
    }

    @Override
    public Reactions retrieveReaction(Long reactionId) {
        return reactionRepository.findById(reactionId).orElse(null);
    }
//
//    @Override
//    public Reactions getReactionByPostAndUser(Long evenenmentId, Long userId) {
//        Evenement evenement = evenementRepository.findById(evenenmentId).get();
//        //User user = userRepository.findById(userId).get();
//        return reactionRepository.findByEvenementAndUserId(evenement,userId);
//    }

    @Override
    public Reactions addReaction(Reactions reaction, Long evenementId, Long userId) {
        Evenement collocation = evenementRepository.findById(evenementId).get();
        reaction.setEvenement(collocation);
        User user = userRespository.findById(userId).get();
        reaction.setUser(user);

        return reactionRepository.save(reaction);
    }

    @Override
    public void removeReaction(Long reactionId) {
        reactionRepository.deleteById(reactionId);
    }


    @Override
    public Reactions modifyReaction(Reactions reaction) {
        return reactionRepository.save(reaction);
    }


    @Override
    public Long countReactionsByPost(Long evenementId) {
        Evenement evenement = evenementRepository.findById(evenementId).get();
        return reactionRepository.countByEvenement(evenement);
    }

    @Override
    public Reactions getReactionByPostAndUser(Long evenementId, Long userId) {
        System.out.println(userId);
        Evenement evenement = evenementRepository.findById(evenementId).get();
        //User user = userRepository.findById(userId).get();
        return reactionRepository.findByEvenementAndUser_IdUser(evenement,userId);
    }
}

