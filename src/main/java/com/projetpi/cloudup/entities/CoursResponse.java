package com.projetpi.cloudup.entities;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoursResponse {
    private long idCours ;
    private Niveau niveau;
    private Spécialité option ;
    private String nomCours;
    private float   price ;
    private String descriptionCours;
    private Modularité type;
    private float dureeC ;
    private long idUser;
}
