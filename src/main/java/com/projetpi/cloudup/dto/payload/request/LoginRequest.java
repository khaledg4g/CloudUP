package com.projetpi.cloudup.dto.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
