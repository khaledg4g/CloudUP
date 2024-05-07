package com.projetpi.cloudup.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Evenement implements   Serializable {

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
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imgevent ;
    @JsonIgnore
    @OneToMany(mappedBy = "evenement", cascade = CascadeType.REMOVE)
    private List<Reactions> reactions;
//    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL)
//    private List<Reactions> reactions;
}
