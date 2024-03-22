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
    private int id_com;
    @Temporal(TemporalType.DATE)
    private Date datePublication= new Date();
    private String content;
    private String keyWords;
    private int votePositif;
    private int voteNegatif;
    private boolean solution;

    @ManyToOne
    @JoinColumn(name = "id_pub",referencedColumnName = "id_pub")
    private Publication publication;

    @ManyToOne
    private User user;



}
