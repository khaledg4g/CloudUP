package com.projetpi.cloudup.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinedPasswordUpdateDto {
    private UpdatePasswordRequest updatePasswordRequest;
    private UserUpdatePWDRequest userUpdatePasswordRequest;

    // Getters and Setters
}
