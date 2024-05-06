package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.commun.PageResponse;
import com.projetpi.cloudup.entities.CoursParticuliers;
import com.projetpi.cloudup.entities.CoursRequest;
import com.projetpi.cloudup.entities.CoursResponse;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.service.CoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class CoursController {

    private final CoursService service;


    @PostMapping("/addCours")
    public ResponseEntity<Long> saveCours(
            @Valid @RequestBody CoursRequest request,
            Authentication user) {
        return ResponseEntity.ok(service.save(request, user));
    }


    @GetMapping("/retrieveByIdCours/{idC}")
    public ResponseEntity<CoursResponse> findCoursByID(@PathVariable Long idC) {
        return ResponseEntity.ok(service.findByID(idC));
    }


    @GetMapping("/retrieveCoursByName/{name}")
    public ResponseEntity<PageResponse<CoursResponse>> findCoursByName(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Authentication connectedUser,
            @PathVariable String name) {
        return ResponseEntity.ok(service.findByName(page, size, connectedUser, name));
    }


    @GetMapping("/retrieveAllCours")
    public ResponseEntity<PageResponse<CoursResponse>> findAllCours(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllCours(page, size, connectedUser));
    }

    @GetMapping("/findCoursByOwner")
    public ResponseEntity<PageResponse<CoursResponse>> findCoursByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findCoursByOwner(page, size, connectedUser));

    }

    @PutMapping("/updateCours/{coursID}")
    public ResponseEntity<Long> updateCours(
            @PathVariable Long coursID,
            @RequestBody CoursRequest request,
            Authentication connectedUser) {
        return ResponseEntity.ok(service.updateCours(coursID,request, connectedUser)) ;
    }

    @DeleteMapping("/deleteCours/{idC}")
    public void deleteCours(@PathVariable long idC, Authentication connectedUser) {
        service.deleteCours(idC, connectedUser);
    }

    @GetMapping("/getTopCourses")
    public List<CoursParticuliers> getTopCourses() {
        return service.findTopCourses();
    }

    @GetMapping("/getTopProfessors")
    public List<User> getTopProfessor() {
        return service.findTopProfessor();
    }






}



