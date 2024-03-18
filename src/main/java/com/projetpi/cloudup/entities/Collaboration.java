package com.projetpi.cloudup.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@EqualsAndHashCode
public class Collaboration {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int    idcol;
    private String nomcol;
    private String desccol ;
    private String imgcol ;


    @ManyToOne
    @JsonIgnore
    private Cours cours;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Partenaires partenaires;
}
