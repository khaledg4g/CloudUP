package com.projetpi.cloudup.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    private Long idToken;

    @ManyToOne
    @JoinColumn(name = "idUser" , nullable = false)
    private User user;

    @Column(name = "codeValidation")
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime validateAt;



}
