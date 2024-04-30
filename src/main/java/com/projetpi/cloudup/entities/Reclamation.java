package com.projetpi.cloudup.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private String objet;
    private CategorieReclamation type;
    private EtatReclamation traite;
    private boolean archive;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime time;
    @JsonIgnore
    @ManyToOne
    private User user;
}