package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.repository.*;
import com.projetpi.cloudup.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Service
@RequestMapping("/auth/profile") // Base path for events
@AllArgsConstructor
public class UserProfileController {
    private AwardRepository awardRepository;
    private SpecialityRepository specialityRepository;
    @Autowired
    private UserProfileService userProfileService;
    private UserRepository userRepository;
    private EducationRepository educationRepository;
//    @Autowired
//    private UserR
    @PutMapping("/update/{id}")
    public User updateEvenement(@RequestBody User evenement, @PathVariable Long id) {
        evenement.setIdUser(id); // Ensure ID is set for update
        return userProfileService.updateProfile(evenement);
    }
@GetMapping("/getA/{id}")
public List<Award> getAw(@PathVariable Long id) {
        return awardRepository.findAllByUserIdUser(id);
}
    @GetMapping("/getS/{id}")
    public List<Speciality> getS(@PathVariable Long id) {
        return specialityRepository.findAllByUserIdUser(id);
    }
    @GetMapping("/getE/{id}")
    public List<Education> getE(@PathVariable Long id) {
        return educationRepository.findAllByUserIdUser(id);
    }
    @PostMapping("/addE/{id}")

    public ResponseEntity<Education> addE(@RequestBody Education award,@PathVariable Long id) {
        User idu= userRepository.findUserByIdUser(id);
        award.setUser(idu);
        return new ResponseEntity<>(userProfileService.addE(award), HttpStatus.CREATED);}
    @PostMapping("/addS/{id}")

    public ResponseEntity<Speciality> addS(@RequestBody Speciality award,@PathVariable Long id) {
        User idu= userRepository.findUserByIdUser(id);
        award.setUser(idu);
        return new ResponseEntity<>(userProfileService.addS(award), HttpStatus.CREATED);}
    @PostMapping("/addaw/{id}")

    public ResponseEntity<Award> addAward(@RequestBody Award award,@PathVariable Long id) {
        User idu= userRepository.findUserByIdUser(id);
        award.setUser(idu);
        return new ResponseEntity<>(userProfileService.addaw(award), HttpStatus.CREATED);}
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

