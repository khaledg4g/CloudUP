package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Reservation;
import com.projetpi.cloudup.entities.ReservationRequest;
import com.projetpi.cloudup.entities.ReservationResponse;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.service.ReservationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class ReservationController {
    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService service;

    @PostMapping("/addReservation/{idCours}")
    public ResponseEntity<Long> saveReservation(
            @Valid @RequestBody ReservationRequest request,
            @PathVariable Long idCours,
            Authentication user) {
        return ResponseEntity.ok(service.save(request, idCours, user));
    }

    @PatchMapping("/updateReservationStatus")
    public ResponseEntity<Long> updateReservationStatus(
            @RequestBody ReservationRequest request,
            Authentication connectedUser) {
        return ResponseEntity.ok(service.updateReservatonStatus(request.statusR().toString(), request.idR(),connectedUser));
    }

    @PatchMapping("/updateReservationDate")
    public ResponseEntity<Long> updateReservationDate(
            @RequestBody ReservationRequest request,
            Authentication connectedUser) {
        return ResponseEntity.ok(service.updateReservationDate(request.dateR(), request.idR(),connectedUser));
    }



    @DeleteMapping("/deleteReservation/{idReservation}")
    public void deleteReservation(@PathVariable long idReservation, Authentication connectedUser) {
        service.deleteReservation(idReservation, connectedUser);
    }

    @GetMapping("/getReservationByOwnerStudent")
    public ResponseEntity<List<ReservationResponse>> getReservationByOwnerStudent(Authentication connectedUser) {
        return ResponseEntity.ok(service.getReservationByOwnerStudent(connectedUser));
    }

    @GetMapping("/getReservationByOwnerProfeesor")
    public ResponseEntity<List<ReservationResponse>> getReservationByOwnerProfeesor(Authentication connectedUser) {
        return ResponseEntity.ok(service.getReservationByOwnerProfeesor(connectedUser));
    }

    @GetMapping("/getReservationStatus/{status}")
    public ResponseEntity<List<ReservationResponse>> getReservationStatus(@PathVariable String status, Authentication connectedUser) {
        return ResponseEntity.ok(service.getReservationStatus(status, connectedUser));
    }


    @GetMapping("/getReservationById/{idReservation}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable long idReservation) {
        return ResponseEntity.ok(service.getReservationById(idReservation));
    }

    @GetMapping("/getMyStudents")
    public List<User> getMyStudents(Authentication connectedUser){
        return service.getMyStudents(connectedUser);

    }

    @PostMapping("/sendPaymentEmail/{idR}")
    private void sendPaymentEmail(@PathVariable Long  idR) throws MessagingException {
        service.sendPaymentEmail(idR);
    }



}
