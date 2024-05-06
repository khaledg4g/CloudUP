package com.projetpi.cloudup.service;
import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.User;

import java.util.List;

public interface IUser {
    public User addU(User u);
    public List<Evenement> retrieveAllP ();

}
