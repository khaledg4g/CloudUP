package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Command implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @OneToMany(fetch = FetchType.EAGER , targetEntity = CommandProduct.class)
    private Set<CommandProduct> commandProducts;

    @ManyToOne(fetch = FetchType.EAGER , targetEntity = User.class)
    private User user;
}
