package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Partenaires;
import com.projetpi.cloudup.repository.PartenairesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartenairesService {
    @Autowired
    private PartenairesRepository partenairesRepository;

    public Partenaires savePartenaires(Partenaires partenaire){
        return partenairesRepository.save(partenaire);
    }

    public List<Partenaires> savePartenaires(List<Partenaires> part){
        return partenairesRepository.saveAll(part);
    }

    public List<Partenaires> getPartenaires(){
        return partenairesRepository.findAll();
    }

    public Partenaires getPartenairesById(int id){
        return partenairesRepository.findById(id).orElse(null);
    }



    public  String deletePartenaires(int id){
        partenairesRepository.deleteById(id);
        return "product removed!!"+id;
    }

    public Partenaires updatePartenaires (Partenaires partenaires){
        Partenaires existingPartenaires=partenairesRepository.findById(partenaires.getId_part()).orElse(null);
        existingPartenaires.setNom(partenaires.getNom());
        existingPartenaires.setDescpart(partenaires.getDescpart());
        existingPartenaires.setImgpart(partenaires.getImgpart());
         return partenairesRepository.save(existingPartenaires);
    }


    public Partenaires getPartenairesBynom(String nom) {
        return partenairesRepository.findByNom(nom);
    }
}
