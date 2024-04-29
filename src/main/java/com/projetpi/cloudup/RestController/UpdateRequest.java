package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest{
    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
     String nom;
    @NotEmpty(message = "Lastname is mandatory")
    @NotBlank(message = "Lastname is mandatory")
    String prenom;
    @NotNull
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Phone number must be in valid international format")
   String phoneNumber;
    @Enumerated(EnumType.STRING)
    Gender gender;
    LocalDate DateOfBirth;
    String aboutMe;
    String city;
    String country;
    int codePostal;

    @Enumerated(EnumType.STRING)
    University college;

    @Enumerated(EnumType.STRING)
    Classe degree;

    @Enumerated(EnumType.STRING)
    Options option;
    String membership;
}
