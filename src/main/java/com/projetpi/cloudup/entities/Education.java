package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private LocalDate dateDebut;
    private LocalDate  dateFin;
    @ManyToOne
    private User user;
}
