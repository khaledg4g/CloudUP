package com.projetpi.cloudup.RestController;
import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.repository.CollaborationRepository;
import com.projetpi.cloudup.repository.ReservationCollaborationRepository;
import com.projetpi.cloudup.repository.UserRepository;
import com.projetpi.cloudup.service.IReservationCollaboration;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projetpi.cloudup.entities.Collaboration;
import java.net.URI;
import java.util.List;


import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@RequestMapping("/auth")
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:53289"})
@RestController
@AllArgsConstructor
public class ReservationCollabController {
    @Autowired
    private IReservationCollaboration reservationCollaborationService;
    @Autowired
    private CollaborationRepository collaborationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationCollaborationRepository reservationCollaborationRepository;
    @Autowired
    private IReservationCollaboration iReservationCollaboration;
    public void ReservationCollaController(IReservationCollaboration reservationCollaborationService) {
        this.reservationCollaborationService = reservationCollaborationService;
    }


    @PostMapping("/addReservationCollaboration/{userId}/{collaborationId}")
    public ReservationCollaboration addReservationCollaboration(
            @PathVariable Long userId,
            @PathVariable int collaborationId) {
        User user = userRepository.findUserByIdUser(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        Collaboration collaboration = collaborationRepository.findById(collaborationId)
                .orElseThrow(() -> new EntityNotFoundException("Collaboration not found with id: " + collaborationId));

        ReservationCollaboration reservationCollaboration = new ReservationCollaboration();
        reservationCollaboration.setUser(user);
        reservationCollaboration.setCollaboration(collaboration);

        return reservationCollaborationRepository.save(reservationCollaboration);
    }



    @GetMapping("/collabreservation")
    public List<ReservationCollaboration> getAllReservationCollaborations() {
        return iReservationCollaboration.getAllReservationCollaborations();
    }


    @GetMapping("/getrescolbyid/{id}")
    public ResponseEntity<ReservationCollaboration> getReservationCollaborationById(@PathVariable Long id) {
        ReservationCollaboration reservationCollaboration = reservationCollaborationService.getReservationCollaborationById(id);
        return ResponseEntity.ok().body(reservationCollaboration);
    }

    @DeleteMapping("/delcolresbyid/{id}")
    public ResponseEntity<Void> deleteReservationCollaboration(@PathVariable Long id) {
        reservationCollaborationService.deleteReservationCollaboration(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/reservationsBYID/{idUser}")
    public List<ReservationCollaboration> getReservationsByUserId(@PathVariable Long idUser) {
        return reservationCollaborationService.findByUserId(idUser);
    }

}
