package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Education;
import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.UtilisateurRepository;
import com.projetpi.cloudup.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Service
@RequestMapping("/auth/profile") // Base path for events
@AllArgsConstructor
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;
//    @Autowired
//    private UserR
    @PutMapping("/update/{id}")
    public User updateEvenement(@RequestBody User evenement, @PathVariable Long id) {
        evenement.setIdUser(id); // Ensure ID is set for update
        return userProfileService.updateProfile(evenement);
    }
    @PostMapping("/addP")
    public ResponseEntity<String> addUserProfile(@RequestBody User user) {
        userProfileService.addUserProfile(user);
        return ResponseEntity.ok("User profile added successfully");
    }
//    @PostMapping("/addE/{id}")
//    public ResponseEntity<String> addUserProfileEducation(@RequestBody Education edu, @PathVariable Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        edu.setUser(user);
//        addEducations(user);
//        return ResponseEntity.ok("User profile education added successfully");
//    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable Long userId) {
        userProfileService.deleteUserProfile(userId);
        return ResponseEntity.ok("User profile deleted successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        User user = userProfileService.getUserProfile(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

