package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Publication;

import java.util.List;

public interface IPublication {
    public Publication addPub(Publication pub) ;
    public Publication updatePub (Publication pub);
    public void deletePub (int idP);



}
