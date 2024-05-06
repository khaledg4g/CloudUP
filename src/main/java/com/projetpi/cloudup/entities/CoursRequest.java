package com.projetpi.cloudup.entities;


public record CoursRequest(

        long idCours,
        Niveau niveau,
        Spécialité option,
        Modularité type,
        String nomCours,
        float   price,
        float dureeC,
        String descriptionCours
        )
{
}
