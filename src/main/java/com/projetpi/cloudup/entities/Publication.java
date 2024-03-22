package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private Date datePub = new Date();
    private String subject;
    private String content;
    private String keyWords;
    private int nbr_vue;
    private int nbr_com;
    private String cloture;

    @ManyToOne
    private Forum forum;

 @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentary> commentaries;

   @ManyToOne
    private User user;
@Transactional
 public void addCommentary(Commentary commentary) {
  commentaries.add(commentary);
  commentary.setPublication(this);
 }
 public void removeCommentary(Commentary commentary) {
  commentaries.remove(commentary);
  commentary.setPublication(null);
  this.setNbr_com(commentaries.size());
 }
}
