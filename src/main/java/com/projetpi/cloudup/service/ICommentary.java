package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;

import java.util.List;

public interface ICommentary {
    public Commentary addCommentToPubUser (Commentary com, Long idpub, Long idu);
    public Commentary updateC (Commentary com);
    public void deleteC (int idC);
    public List<Commentary> retrieveByTags(String tags);

    public List<Commentary> findByContent(String content);

    public void upvoteCommentary(int commentId);
    public void downvoteCommentary(int commentId);


}
