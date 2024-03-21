package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Publication implements Serializable {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pub;
    @Temporal(TemporalType.DATE)
    private Date datePub;
    private String sujet;
    private String contenuP;
    private String keyWords;
    private int nbr_vue;
    private int nbr_com;
    private String cloture;

    @ManyToOne
    private Forum forum;

    @OneToMany (mappedBy = "publication")
    private List<Commentary> commentaries;

   @ManyToOne
    private User user;
}
