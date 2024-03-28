package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentaryDTO {
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
    private int UserID;

}


