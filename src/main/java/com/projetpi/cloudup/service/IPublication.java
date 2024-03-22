package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Publication;

public interface IPublication {
    public Publication addPubtoForum(Publication pub, Long idf);
    public Publication updatePub (Publication pub);
    public void deletePub (int idP);
    public void incrementViews(Long publicationId);



}
