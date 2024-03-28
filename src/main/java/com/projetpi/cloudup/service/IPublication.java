package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;

import java.util.List;

public interface IPublication {
    public Publication addPubtoForumUser(Publication pub, Long idf, Long idu);
    public Publication updatePub (Publication pub);
    public void deletePub (int idP);
    public void incrementViews(Long publicationId);
    public void markSolutionAndClosePublication();

    public List<Commentary> retrieveAllCByPub(int idpub);

    Publication findById(int idpub);
}
