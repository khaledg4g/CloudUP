package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Partenaires;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPartenaires {




    public String savePartenairesbeltaswira(MultipartFile file, String nom, String descpart);
    public Partenaires savePartenaires(Partenaires partenaire);

    public List<Partenaires> savePartenaires(List<Partenaires> part);

    public List<Partenaires> getPartenaires();

    public Partenaires getPartenairesById(int id);



    public  String deletePartenaires(int id);

    public Partenaires updatePartenaires (Partenaires partenaires);

    public Partenaires getPartenairesBynom(String nom);





}
