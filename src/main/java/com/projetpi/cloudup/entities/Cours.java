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
public class Cours {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int    idcour;
    private String nom;

    @OneToMany(mappedBy = "cours")
    private List<Collaboration> collaborations;
}
