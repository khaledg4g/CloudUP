package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Publication;

public interface IPublication {
    public Publication addPubtoForumUser(Publication pub, Long idf, Long idu);
    public Publication updatePub (Publication pub);
    public void deletePub (int idP);
    public void incrementViews(Long publicationId);
    public void markSolutionAndClosePublication();



}
