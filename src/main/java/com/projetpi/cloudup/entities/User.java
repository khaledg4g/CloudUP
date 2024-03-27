package com.projetpi.cloudup.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Set<Publication> publications;

    @OneToMany(mappedBy = "user")//, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Commentary>commentaries;



}
