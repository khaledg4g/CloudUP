package com.projetpi.cloudup.entities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private long idR;
    private Date dateR;
    private Etat statusR;
    private long id_etudiant;
    private long id_professeur;
    private long id_cours;
}
