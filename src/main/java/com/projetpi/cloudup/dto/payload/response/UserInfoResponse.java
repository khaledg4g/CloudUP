package com.projetpi.cloudup.dto.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private List<String> roles;
}
