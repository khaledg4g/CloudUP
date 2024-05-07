package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.CategorieEvenement;
import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.repository.CategorieEvenementRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CategorieEvenementServiceIMP implements ICategorieEvenement{
    @Autowired
    private CategorieEvenementRepository categorieEvenementRepository;

    @Override
    public CategorieEvenement addCategorieEvenement(CategorieEvenement categorieEvenement) {
        return categorieEvenementRepository.save(categorieEvenement);
    }

    @Override
    public CategorieEvenement retrieveCategorieEvenementById(Long idCategorieEvenement) {
        return categorieEvenementRepository.findById(idCategorieEvenement).orElse(null);
    }

    @Lock(value = LockModeType.WRITE)
    @Transactional
    public CategorieEvenement updateCategorieEvenement(CategorieEvenement evenement) {
        Optional<CategorieEvenement> existingEvenementOptional = categorieEvenementRepository.findById(evenement.getId());
        if (existingEvenementOptional.isPresent()) {
            CategorieEvenement existingEvenement = existingEvenementOptional.get();
            updateEvenementFields(evenement, existingEvenement);
            return categorieEvenementRepository.save(existingEvenement);
        } else {
            return null;
        }
    }

    private void updateEvenementFields(CategorieEvenement updatedEvenement, CategorieEvenement existingEvenement) {
        for (Field field : updatedEvenement.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object updatedValue = field.get(updatedEvenement);
                if (updatedValue != null) {
                    field.set(existingEvenement, updatedValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<CategorieEvenement> retrieveAllCategoriesEvenement() {
        return categorieEvenementRepository.findAll();
    }

    @Override
    public void deleteCategorieEvenement(Long idCategorieEvenement) {
        categorieEvenementRepository.deleteById(idCategorieEvenement);
    }
}

