package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.service.AuthentificationService;
import com.projetpi.cloudup.service.IUser;
import com.projetpi.cloudup.service.JwtService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@CrossOrigin("*")
@RequestMapping("/auth")
public class UserRestController {
    @Autowired
    public IUser iUser;
    @Autowired
    private JwtService service;

    @Autowired
    public UserRestController(IUser iUser) {
        this.iUser = iUser;
    }

    @PostMapping("/addUser")
    public User addU (@RequestBody User u) {
        return iUser.addU(u);
    }

    @GetMapping("/retrieveAllPtoUser")
    public List<Publication> retrieveAllP(){
        return iUser.retrieveAllP();
    }

    @GetMapping("/retrieveAllCtoUser")
    public List<Commentary> retrieveAllC(){
        return iUser.retrieveAllC();
    }

    @Autowired
    private JwtService jwtService;

   /* @GetMapping("/api/user")
    public ResponseEntity<Long> getUserIdFromToken(@RequestHeader("Authorization") String token) {
        // Extraire l'ID de l'utilisateur à partir du token
        Long userId = jwtService.extractUserId(token);
        if (userId != null) {
            // L'ID de l'utilisateur a été extrait avec succès, retourner l'ID
            return ResponseEntity.ok(userId);
        } else {
            // Le token est invalide ou l'ID de l'utilisateur n'a pas pu être extrait
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }*/
    @GetMapping("/getTokenAndReturnID")
    public ResponseEntity<Long> getTokenAndReturnId(@RequestParam String token) {
        Long userId = service.getUserIdByToken(token);
        return ResponseEntity.ok(userId);
    }

}
