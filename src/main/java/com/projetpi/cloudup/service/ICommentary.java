package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;

import java.util.List;

public interface ICommentary {
    public Commentary addCommentToPubUser (Commentary com, Long idpub, Long idu);
    public Commentary updateC (Commentary com);
    public void deleteC (int idC, int idpub);
    public List<Commentary> retrieveByTags(Long idpub, String tags);

    public List<Commentary> findByContent(Long idpub, String content);

    public void upvoteCommentary(int commentId);
    public void downvoteCommentary(int commentId);
    public int getCommentLikes(int idCom);
    public int getCommentDislikes(int idCom);

    String getUsername(long userId);
}
