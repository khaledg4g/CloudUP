package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Reservation implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID Réservation")
    private long idR;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Date Réservation")
    private Date dateR;
    @Enumerated(EnumType.STRING)
    @Column(name = "Status ")
    private Etat statusR;

    @ManyToOne
    @JoinColumn(name = "id_professeur")
    private User professeur;
    @ManyToOne
    @JoinColumn(name = "id_etudiant")
    private User etudiant;
    @ManyToOne
    @JoinColumn(name = "id_cours")
    private CoursParticuliers cours;

}
