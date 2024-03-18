package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int    iduser;
    private String nom;

    @OneToMany(mappedBy = "user")
    private List<Collaboration> collaborations;

    @OneToMany(mappedBy = "user")
    private List<Publication> publications;

    @OneToMany(mappedBy = "user")
    private List<Commentaire>commentaires;
}
