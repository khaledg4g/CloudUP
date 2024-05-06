package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Partenaires implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private int    id_part;
    private String nom;
    private String descpart ;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imgpart;


}
