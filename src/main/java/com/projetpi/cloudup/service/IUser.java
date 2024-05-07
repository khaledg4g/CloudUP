package com.projetpi.cloudup.service;
import com.projetpi.cloudup.entities.Evenement;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.Role;
import com.projetpi.cloudup.entities.User;

import java.util.List;

public interface IUser {
    public User addU(User u);
    public List<Evenement> retrieveAllP ();

    User addU(User u);
    List<Publication> retrieveAllP ();

    List<Commentary> retrieveAllC ();
    Role getRole (long idUser);
}
