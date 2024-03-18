package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Publication;

public interface IPublication {
    public Publication addPub(Publication pub) ;
    public Publication updatePub (Publication pub);
    public void deletePub (int idP);



}
