package com.projetpi.cloudup.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpi.cloudup.service.ClassListener;
import com.projetpi.cloudup.service.CommentListener;
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
@EntityListeners(CommentListener.class)
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
    private int gotMail=0;

    @ManyToOne
            @JsonIgnore
    Publication publication;

    @ManyToOne
            @JsonIgnore
    User user;



}
