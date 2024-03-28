package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Commentary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCom;
    @Temporal(TemporalType.DATE)
    private Date datePublication= new Date();
    private String content;
    private String tags;
    private int votePositif;
    private int voteNegatif;
    private String solution="false";
    private String username;
    private int idpub;

    @ManyToOne
    Publication publication;

    @ManyToOne
    User user;



}
