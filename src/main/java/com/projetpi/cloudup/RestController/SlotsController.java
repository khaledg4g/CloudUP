package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.TimeSlotRequest;
import com.projetpi.cloudup.entities.TimeSlotResponse;
import com.projetpi.cloudup.service.SlotsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SlotsController {
    private final SlotsService service;


    @PostMapping("/addSlots")
    public ResponseEntity<Long> saveSlot(
            @Valid @RequestBody TimeSlotRequest request,
            Authentication user) {
        return ResponseEntity.ok(service.saveSlot(request, user));
    }

    @DeleteMapping("/deleteSlot/{idS}")
    public void deleteCours(@PathVariable long idS, Authentication connectedUser) {
        service.deleteSlot(idS, connectedUser);
    }

    /*   To be implemented on scheduling component  */
    @GetMapping("/getSlotsOfConnectedUserOnly/{day}")
    public ResponseEntity<List<TimeSlotResponse>> getSlotsOfConnectedUserOnly(@PathVariable String day
            , Authentication connectedUser) {
        return ResponseEntity.ok(service.getSlotsOfConnectedUserOnly(day, connectedUser));

    }

    /*   To be implemented on Booking component  */

    @GetMapping("/getSlotsForBooking/{userID}/{day}")
    public ResponseEntity<List<TimeSlotResponse>> getSlotsForBooking(@PathVariable Long userID
            , @PathVariable String day) {
        return ResponseEntity.ok(service.getSlotsForBooking(userID, day));

    }


}
