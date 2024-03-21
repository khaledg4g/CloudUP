package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentaire;

import java.util.List;

public interface ICommentaire {
    public Commentaire addC (Commentaire com);
    public Commentaire updateC (Commentaire com);
    public void deleteC (int idC);
    public List<Commentaire> retrieveAllC ();
    public List<Commentaire> retrieveByKeyWords(String keyWords);

}
