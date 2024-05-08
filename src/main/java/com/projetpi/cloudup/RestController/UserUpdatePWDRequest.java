package com.projetpi.cloudup.RestController;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdatePWDRequest {

    private String nom;
    private String prenom;
    private String email;
    private String phoneNumber;
}
