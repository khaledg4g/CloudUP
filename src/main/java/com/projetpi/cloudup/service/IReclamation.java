package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.EtatReclamation;
import com.projetpi.cloudup.entities.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IReclamation {
    public Reclamation AjouterReclamation(Reclamation reclamation);

    public List<Reclamation> RetrieveAll();

    public Reclamation RetrieveById(Integer id);

    public Reclamation UpdateReclamation(Reclamation reclamation);

    public void DeleteReclamation(Integer id);

    public List<Reclamation> RetrieveObjet(String objet);

    public Page<Reclamation> GetAllWithPagination(int size, int page, String sortBy, String sortOrder);

    public Reclamation SetReclam(Reclamation reclamation);

    public List<Reclamation> ArchiveReclam();

    public Page<Reclamation> GetArchives(int size, int page, String sortBy, String sortOrder);
}
