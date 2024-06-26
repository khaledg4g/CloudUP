package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.CommentaryDTO;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.service.IPublication;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@NoArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping("/auth")
public class PublicationRestController {
    public IPublication iPublication;

    @Autowired
    public PublicationRestController(IPublication iPublication) {
        this.iPublication = iPublication;
    }


    @PostMapping("/{idu}/{idf}/addPub")
    public Publication addPub(@RequestBody Publication pub, @PathVariable Long idf, @PathVariable Long idu) {
        return iPublication.addPubtoForumUser(pub,idf,idu);
    }

    @PostMapping("/upload/{idpub}")
    public void uploadImage(@RequestParam MultipartFile imageFile, @PathVariable int idpub) throws IOException {
       iPublication.uploadImage(imageFile, idpub);
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
    @GetMapping("/retrieveALLC/{idpub}")
    public List<CommentaryDTO> retrieveAllC(@PathVariable int idpub) {
        Publication publication = iPublication.findById(idpub);
        if (publication != null) {
            List<Commentary> commentaries = iPublication.retrieveAllCByPub(publication.getIdpub());
            List<CommentaryDTO> commentaryDTOs = commentaries.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            return commentaryDTOs;
        }
        return Collections.emptyList();
    }
    @GetMapping("/retrieveByUser/{idUser}")
    public List<Publication> retrievePubByUser (@PathVariable long idUser){
        return iPublication.fetchPubByIDUser(idUser);
    }

    private CommentaryDTO convertToDto (Commentary commentary) {
        CommentaryDTO commentaryDTO = new CommentaryDTO();
        commentaryDTO.setIdCom(commentary.getIdCom());
        commentaryDTO.setDatePublication(commentary.getDatePublication());
        commentaryDTO.setContent(commentary.getContent());
        commentaryDTO.setTags(commentary.getTags());
        commentaryDTO.setVotePositif(commentary.getVotePositif());
        commentaryDTO.setVoteNegatif(commentary.getVoteNegatif());
        commentaryDTO.setSolution(commentary.getSolution());
        User user = commentary.getUser();
        if (user != null) {
            commentaryDTO.setUserID(Math.toIntExact(user.getIdUser()));
            commentaryDTO.setUsername(user.getNom()+" "+ user.getPrenom());
            log.info("commentaire " + commentaryDTO.getUsername());
        } else {
            commentaryDTO.setUserID(0);
            commentaryDTO.setUsername("");
        }
        commentaryDTO.setIdpub(commentaryDTO.getIdpub());
return commentaryDTO;
    }
}