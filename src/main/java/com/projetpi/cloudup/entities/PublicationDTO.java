package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
public class PublicationDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpub;
    @Temporal(TemporalType.DATE)
    private Date datePub = new Date();
    private String subject;
    private String content;
    private String tags;
    private int nbr_vue;
    private int nbr_com;
    @Enumerated( EnumType.STRING)
    private categories categories;
    private String closed="false";
    private int forumId;
    private int UserId;
    private String username;
}
