package com.projetpi.cloudup.entities;

import com.projetpi.cloudup.RestController.UpdateRequest;
import com.projetpi.cloudup.service.FileUtilsYass;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
@Builder
public class UserMapper {

    public User toUser(UpdateRequest request) {
        return User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .phoneNumber(request.getPhoneNumber())
                .DateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .aboutMe(request.getAboutMe())
                .city(request.getCity())
                .country(request.getCountry())
                .codePostal(request.getCodePostal())
                .college(request.getCollege())
                .degree(request.getDegree())
                .option(request.getOption())
                .membership(request.getMembership())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .idUser(user.getIdUser())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .motDePasse(user.getMotDePasse())
                .phoneNumber(user.getPhoneNumber())
                .image(FileUtilsYass.readFileFromLocation(user.getImage()))
                .build();
    }
}
