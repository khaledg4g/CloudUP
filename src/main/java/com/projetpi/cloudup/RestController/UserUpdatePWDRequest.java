package com.projetpi.cloudup.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserUpdatePWDRequest {

    private String nom;
    private String prenom;
    private String email;
    private String phoneNumber;
}
