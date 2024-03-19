package com.projetpi.cloudup.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Reclamation implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String description;
    private CategorieReclamation type;
    private EtatReclamation traite;
    @JsonIgnore
    @ManyToOne
    private User user;


}