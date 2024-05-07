package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.CategorieEvenement;

import java.util.List;

public interface ICategorieEvenement {
    public CategorieEvenement addCategorieEvenement(CategorieEvenement categorieEvenement);
    public CategorieEvenement retrieveCategorieEvenementById(Long idCategorieEvenement);
    public CategorieEvenement updateCategorieEvenement(CategorieEvenement categorieEvenement);
    public List<CategorieEvenement> retrieveAllCategoriesEvenement();
    public void deleteCategorieEvenement(Long idCategorieEvenement);
}
