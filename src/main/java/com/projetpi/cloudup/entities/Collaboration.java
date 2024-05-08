package com.projetpi.cloudup.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpi.cloudup.service.ClassListener;
import com.projetpi.cloudup.service.ClassListenerKhaled;
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
@EntityListeners(ClassListenerKhaled.class)

public class Collaboration implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int    idcol;
    private String nomcol;
    private String desccol ;
    private Date datecol;
    private String placecol;
    private float prixcol;
    private int votePositif;
    private int voteNegatif;
    private int nbrres;
    private String qrString;
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
