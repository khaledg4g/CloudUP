package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.service.IPublication;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@CrossOrigin("*")
public class PublicationRestController {
    public IPublication iPublication;

    @Autowired
    public PublicationRestController(IPublication iPublication) {
        this.iPublication = iPublication;
    }


    @PostMapping("/{idu}/{idf}/addPub")
    public Publication addPub(@RequestBody Publication pub, @PathVariable Long idf,@PathVariable Long idu) {
        return iPublication.addPubtoForumUser(pub,idf,idu);
    }

    @PutMapping("/updatePub")
    public Publication updatePub(@RequestBody Publication pub) {
        return iPublication.updatePub(pub);
    }

    @DeleteMapping("/deletePub/{idP}")
    public void deletePub(@PathVariable int idP) {
        iPublication.deletePub(idP);
    }

    @PutMapping("/publications/{id}/increment-views")
    public ResponseEntity<?> incrementViewsForPublication(@PathVariable Long id) {
        iPublication.incrementViews(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/mark-solution-and-close")
    public ResponseEntity<String> markSolutionAndClosePublication() {
        iPublication.markSolutionAndClosePublication();
        return ResponseEntity.ok("Method executed successfully!");
    }
}