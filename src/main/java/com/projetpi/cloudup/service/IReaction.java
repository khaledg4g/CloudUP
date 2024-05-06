package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Reactions;

import java.util.List;

public interface IReaction {
    List<Reactions> retrieveAllReactions();
    Reactions retrieveReaction(Long reactionId);
//    Reactions getReactionByPostAndUser(Long post, Long userId);
    Reactions addReaction(Reactions reaction, Long postId, Long userId);
    void removeReaction(Long reactionId);
    Reactions modifyReaction(Reactions reaction);
    Long countReactionsByPost (Long postId);
    Reactions getReactionByPostAndUser(Long evenementId, Long userId);
}

