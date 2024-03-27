package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class User implements Serializable {
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Id

 private int    id;
 private String nom;
 private String mail;
 private int nbr_pub;
 private int nbr_com;


    @OneToMany(mappedBy = "user")// ,cascade = CascadeType.ALL)
    private Set<Publication> publications;

    @OneToMany(mappedBy = "user")//, cascade = CascadeType.ALL)
    private Set<Commentary>commentaries;



}
