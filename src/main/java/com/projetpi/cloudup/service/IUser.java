package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;

import java.util.List;

public interface IUser {
    public User addU(User u);
    public List<Publication> retrieveAllP ();

    public List<Commentary> retrieveAllC ();
}
