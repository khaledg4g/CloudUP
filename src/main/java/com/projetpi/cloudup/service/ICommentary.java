package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;

import java.util.List;

public interface ICommentary {
    public Commentary addC (Commentary com);
    public Commentary updateC (Commentary com);
    public void deleteC (int idC);
    public List<Commentary> retrieveAllC ();
    public List<Commentary> retrieveByKeyWords(String keyWords);

}
