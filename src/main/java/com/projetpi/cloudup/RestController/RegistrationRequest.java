package com.projetpi.cloudup.RestController;


import com.projetpi.cloudup.entities.Role;
import com.projetpi.cloudup.utilities.ValidEmailDomain;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    @NotBlank(message = "Firstname is mandatory")
    private String nom;

    @NotEmpty(message = "Lastname is mandatory")
    @NotBlank(message = "Lastname is mandatory")
    private String prenom;

    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    @ValidEmailDomain(domain = "esprit.tn")
    private String email;

    @NotNull
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Phone number must be in valid international format")
    private String phoneNumber;


    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String motDePasse;

    private Role roles;
}
