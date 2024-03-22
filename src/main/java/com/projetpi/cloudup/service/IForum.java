package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Publication;

import java.util.List;

public interface IForum {
    public List<Publication> retrieveAll ();
    public List<Publication> retrieveByKeyWords(String keyWords);
    public List<Publication> retrieveBySubject(String subject);
    public List<Publication> retrieveByContent (String content);

}
