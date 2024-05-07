package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Partenaires;
import com.projetpi.cloudup.repository.PartenairesRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PartenairesService implements IPartenaires {
    @Autowired
    private PartenairesRepository partenairesRepository;




    public String savePartenairesbeltaswira(MultipartFile file, String nom, String descpart)
    {
        Partenaires p = new Partenaires();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            p.setImgpart(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.setDescpart(descpart);

        p.setNom(nom);


        partenairesRepository.save(p);
        return fileName;
    }
    public Partenaires savePartenaires(Partenaires partenaire){
        return partenairesRepository.save(partenaire);
    }

    public List<Partenaires> savePartenaires(List<Partenaires> part){
        return partenairesRepository.saveAll(part);
    }

    public List<Partenaires> getPartenaires(){
        return partenairesRepository.findAll();
    }

    @Override
    public Partenaires getPartenairesById(int id) {
        return partenairesRepository.findById(id).orElse(null);
    }


    @Override
    public String deletePartenaires(int id) {
        partenairesRepository.deleteById(id);
        return "product removed!!" + id;
    }




    public Partenaires getPartenairesBynom(String nom) {
        return partenairesRepository.findByNom(nom);
    }


    @Lock(value = LockModeType.WRITE)
    @Transactional
    public Partenaires updatePartenaires(Partenaires partenaires) {
        Optional<Partenaires> existingPartenairesOptional = partenairesRepository.findById(partenaires.getId_part());
        if (existingPartenairesOptional.isPresent()) {
            Partenaires existingPartenaires = existingPartenairesOptional.get();
            updatePartenaireFields(partenaires, existingPartenaires);
            return partenairesRepository.save(existingPartenaires);
        } else {
            return null;
        }
    }

    private void updatePartenaireFields(Partenaires updatedPartenaires, Partenaires existingPartenaires) {
        for (Field field : updatedPartenaires.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object updatedValue = field.get(updatedPartenaires);
                if (updatedValue != null) {
                    field.set(existingPartenaires, updatedValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }




}
