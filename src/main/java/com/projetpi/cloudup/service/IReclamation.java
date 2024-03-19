package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Reclamation;

import java.util.List;

public interface IReclamation {
public Reclamation AjouterReclamation (Reclamation reclamation);
public List<Reclamation> RetrieveAll ();
public Reclamation RetrieveById (Integer id);
public Reclamation UpdateReclamation (Reclamation reclamation);
public void DeleteReclamation (Integer id);
}
