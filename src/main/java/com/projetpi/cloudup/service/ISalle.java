package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Salle;

import java.util.List;

public interface ISalle {
    public Salle addSalle(Salle salle);
    public Salle retrieveSalleById(Long idSalle);
    public Salle updateSalle(Salle salle);
    public List<Salle> retrieveAllSalles();
    public void deleteSalle(Long idSalle);

}
