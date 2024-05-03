package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPublication {
    public Publication addPubtoForumUser( Publication pub, Long idf, Long idu);
    public void uploadImage (MultipartFile imageFile, long idpub) throws IOException;
    public Publication updatePub (Publication pub);
    public void deletePub (long idP);
    public void incrementViews(Long publicationId);
    public void markSolutionAndClosePublication();

    public List<Commentary> retrieveAllCByPub(int idpub);

    Publication findById(int idpub);

    List<Publication> fetchPubByIDUser (long idUser);
}
