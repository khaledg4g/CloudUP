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
public class Partenaires {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int    id_part;
    private String nom;
    private String descpart ;
    private String imgpart ;

    @OneToMany(mappedBy = "partenaires")
    private List<Collaboration> collaborations;
}
