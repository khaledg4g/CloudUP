package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.Reactions;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.service.EvenementServiceIMP;
import com.projetpi.cloudup.service.IEvenement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Service
@RequestMapping("/auth/evenement") // Base path for events
@AllArgsConstructor

public class EvenementController {
    @Autowired

    private EvenementServiceIMP  ievenementService;

    @PostMapping("/add")
    public Evenement addEvenement(@RequestBody Evenement evenement) {
        return ievenementService.addEvenement(evenement);
    }
    @PostMapping("/add2")
    public String addCollaboration(@RequestParam("file") MultipartFile file,
                                   @RequestParam("nom") String nomcol,
                                   @RequestParam("description") String desccol,
                                   @RequestParam("datedebut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datecol,
                                   @RequestParam("datefin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datefin,
                                   @RequestParam("lieu") String placecol,
                                   @RequestParam("maxparticipant") long prixcol,
                                   // @RequestParam("cours_idcour") int cours_idcour,
                                   @RequestParam("organisateur_id_user") long partenaires_id_part,
                                   @RequestParam("categorie_id") long nbrres)
    // @RequestParam("user_iduser") int user_iduser
    {
        return ievenementService.saveEvenement(file, nomcol,desccol,datecol,datefin,   placecol,   prixcol,     partenaires_id_part,nbrres    );
    }

    @GetMapping("/{id}")
    public Evenement retrieveEvenementById(@PathVariable Long id) {
        return ievenementService.retrieveEvenementById(id);
    }

    @GetMapping("/all")
    public List<Evenement> retrieveAllEvenements() {
        return ievenementService.retrieveAllEvenements();
    }

    @PutMapping("/update/{id}")
    public Evenement updateEvenement(@RequestBody Evenement evenement, @PathVariable Long id) {
        evenement.setId(id); // Ensure ID is set for update
        return ievenementService.updateEvenement(evenement);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEvenement(@PathVariable Long id) {
        ievenementService.deleteEvenement(id);
    }

    @PostMapping("/join/{evenementId}/{utilisateurId}")
    public Evenement addParticipant(@PathVariable Long evenementId, @PathVariable Long utilisateurId) {
        return ievenementService.addParticipant(evenementId, utilisateurId);
    }

    @DeleteMapping("/leave/{evenementId}/{utilisateurId}")
    public Evenement removeParticipant(@PathVariable Long evenementId, @PathVariable Long utilisateurId) {
        return ievenementService.removeParticipant(evenementId, utilisateurId);
    }
    @GetMapping("/isParticipating/{evenementId}/{utilisateurId}")
    public ResponseEntity<Boolean> isParticipating(@PathVariable Long evenementId, @PathVariable Long utilisateurId) {
        boolean isParticipating = ievenementService.isParticipating(evenementId, utilisateurId);
        return ResponseEntity.ok(isParticipating);
    }
    @GetMapping("/participants/{eventId}/count")
    public ResponseEntity<Integer> getParticipantCount(@PathVariable Long eventId) {
        int participantCount = ievenementService.getParticipantCount(eventId);
        return ResponseEntity.ok(participantCount);
    }
    @GetMapping("/Top3")
    public List<Evenement> getAllEvents() {
        return ievenementService.findAllEventsWithParticipantCount();
    }
    @PostMapping("/report/{eventId}")
    public ResponseEntity<Evenement> reportEvent(@PathVariable Long eventId) {
        Evenement reportedEvent = ievenementService.reportEvent(eventId);
        if (reportedEvent != null) {
            return ResponseEntity.ok(reportedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    }


