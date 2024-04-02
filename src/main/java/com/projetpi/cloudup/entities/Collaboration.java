package com.projetpi.cloudup.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Collaboration implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int    idcol;
    private String nomcol;
    private String desccol ;
    private Date datecol;
    private String placecol;
    private float prixcol;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imgcol ;

    @ManyToOne

    private Cours cours;

    @ManyToOne

    private User user;

    @ManyToOne

    private Partenaires partenaires;
}
