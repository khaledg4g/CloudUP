package com.projetpi.cloudup.service;
import com.projetpi.cloudup.entities.*;

import java.util.List;

public interface IUser {


    User addU(User u);
    List<Publication> retrieveAllP ();

    List<Commentary> retrieveAllC ();
    Role getRole (long idUser);
}
