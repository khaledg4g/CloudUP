package com.projetpi.cloudup.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Reclamation implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String description;
    private CategorieReclamation type;
    private EtatReclamation traite;
    @JsonIgnore
    @ManyToOne
    private User user;


}