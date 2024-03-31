package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.EtatReclamation;
import com.projetpi.cloudup.entities.Reclamation;
import com.projetpi.cloudup.repository.ReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ReclamationServiceIMP implements IReclamation{
    private ReclamationRepository reclamationRepository;
    @Override
    public Reclamation AjouterReclamation(Reclamation reclamation) {
        reclamation.setTraite(EtatReclamation.EnAttente);
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> RetrieveAll() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation RetrieveById(Integer id) {
        return reclamationRepository.findById(id).orElse(null);
    }

    @Override
    public Reclamation UpdateReclamation(Reclamation reclamation) {
        Reclamation reclamation1 = reclamationRepository.findById(reclamation.getId()).orElse(null);
        reclamation1.setId(reclamation.getId());
        reclamation1.setObjet(reclamation.getObjet());
        reclamation1.setDescription(reclamation.getDescription());
        reclamation1.setType(reclamation.getType());

        return reclamationRepository.save(reclamation1);
    }

    @Override
    public void DeleteReclamation(Integer id)
    {
        reclamationRepository.deleteById(id);
    }


}
