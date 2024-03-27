package com.projetpi.cloudup.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Forum implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_Forum;
    private int nbr_pub;
@JsonIgnore
    @OneToMany(mappedBy="forum")//, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Publication> publications;


}

