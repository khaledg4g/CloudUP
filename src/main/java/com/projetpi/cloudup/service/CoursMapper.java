package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.CoursParticuliers;
import com.projetpi.cloudup.entities.CoursRequest;
import com.projetpi.cloudup.entities.CoursResponse;
import org.springframework.stereotype.Service;

@Service
public class CoursMapper {

    public CoursParticuliers toCours(CoursRequest request) {
        return CoursParticuliers.builder()
                .idCours(request.idCours())
                .nomCours(request.nomCours())
                .descriptionCours(request.descriptionCours())
                .price(request.price())
                .dureeC(request.dureeC())
                .niveau(request.niveau())
                .option(request.option())
                .type(request.type())
                .build();

    }

    public CoursResponse toCoursResponse(CoursParticuliers coursParticuliers) {
        return CoursResponse.builder()
                .idCours(coursParticuliers.getIdCours())
                .nomCours(coursParticuliers.getNomCours())
                .descriptionCours(coursParticuliers.getDescriptionCours())
                .price(coursParticuliers.getPrice())
                .niveau(coursParticuliers.getNiveau())
                .option(coursParticuliers.getOption())
                .type(coursParticuliers.getType())
                .dureeC(coursParticuliers.getDureeC())
                .usernameCours(coursParticuliers.getProfesseur().fullName()) //added field
                .idUser(coursParticuliers.getProfesseur().getIdUser())
                .build();
    }
}
