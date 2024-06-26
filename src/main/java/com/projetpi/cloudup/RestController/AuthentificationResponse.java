package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthentificationResponse {

    private String token;
    private User user;

}
