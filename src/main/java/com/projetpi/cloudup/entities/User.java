package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int    iduser;
    private String nom;


}
