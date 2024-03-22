package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;

import java.util.List;

public interface ICommentary {
    public Commentary addCommentToPub (Commentary com, Long idpub);
    public Commentary updateC (Commentary com);
    public void deleteC (int idC);
    public List<Commentary> retrieveAllC ();
    public List<Commentary> retrieveByKeyWords(String keyWords);

    public List<Commentary> findByContent(String content);

    public void upvoteCommentary(int commentId);
    public void downvoteCommentary(int commentId);
    public void markAsSolution (Commentary com);

}
