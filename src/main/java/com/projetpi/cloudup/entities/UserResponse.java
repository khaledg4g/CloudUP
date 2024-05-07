package com.projetpi.cloudup.entities;

import lombok.*;

import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {

    private Long idUser;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String phoneNumber;
    private byte[] image;
    private Gender gender;
    private LocalDate DateOfBirth;
    private String aboutMe;
    private String city;
    private String country;
    private int codePostal;
    private University college;
    private Classe degree;
    private Options option;
    private String membership;


}
