package tn.esprit.pi.services;

import tn.esprit.pi.entities.Publication;

import java.util.List;

public interface IPublication {
    public Publication addPub(Publication pub) ;
    public Publication updatePub (Publication pub);
    public void deletePub (int idP);



}
