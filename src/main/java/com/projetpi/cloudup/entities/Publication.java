package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Publication implements Serializable {
 @Serial
 private static final long serialVersionUID=1L;
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pub;


    @Temporal(TemporalType.DATE)
    private Date datePub = new Date();
    private String subject;
    private String content;
    private String keyWords;
    private int nbr_vue;
    private int nbr_com;
    private String closed="false";

    @ManyToOne
    Forum forum;

 @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Commentary> commentaries;

   @ManyToOne
   User user;


}
