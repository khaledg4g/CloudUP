package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long reports;

    private String nom;
    private long maxparticipant;


    private String description;

    private LocalDate dateDebut;

    private LocalDate  dateFin;

        private String lieu;

    private TypeEvenement type;

    @ManyToOne
    private User organisateur;

   @JoinTable(
            name = "evenement_participant",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
  @ManyToMany

  private List<User> participants;

    @ManyToOne
    private CategorieEvenement categorie;

    @ManyToOne
    private Salle salle;

}
