package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class CoursParticuliers implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long idCours ;
    private Niveau niveau;
    private Spécialité option ;
    private String nomCours;
    private float   price ;
    @Column(name="Description")
    private String descriptionCours;
    @Enumerated(EnumType.STRING)
    @Column(name="Modularité")
    private Modularité type;
    @Column(name="Durée Cours")
    private float dureeC ;
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User professeur;
   /*@OneToMany(cascade = CascadeType.ALL, mappedBy= "coursParticuliers")
    private List<Reservation> reservationList;*/

}
