package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.repository.CommentaryRepository;
import com.projetpi.cloudup.service.ICommentary;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@NoArgsConstructor
@CrossOrigin("*")
@RequestMapping("/auth")
public class CommentaryRestController {

    public ICommentary iCommentary;

    @Autowired
    public CommentaryRestController(ICommentary iCommentary){
        this.iCommentary=iCommentary;
    }

   @PostMapping("/addCom/{idu}/{idpub}")
    public Commentary addC (@RequestBody Commentary com, @PathVariable Long idpub, @PathVariable Long idu){
        return iCommentary.addCommentToPubUser(com,idpub, idu);
    }
    @PutMapping("/updateC")
    public Commentary updateC (@RequestBody Commentary com){
        return iCommentary.updateC(com);
    }
    @DeleteMapping("/deleteC/{idC}/{idpub}")
    public void deleteC (@PathVariable int idC, @PathVariable int idpub){
        iCommentary.deleteC (idC,idpub);
    }





    @GetMapping("/retrieveByTagsC/{idpub}")
    public List<Commentary> retrieveByTags(@PathVariable Long idpub, @RequestParam String tags) {

            return iCommentary.retrieveByTags(idpub,tags);

    }
    @GetMapping("/retrieveByContentC/{idpub}")
    public List<Commentary> retrieveByContent(@PathVariable Long idpub, @RequestParam String content){
        return iCommentary.findByContent(idpub, content);
    }

    @PutMapping("/{commentId}/upvote")
    public ResponseEntity<?> upvoteCommentary(@PathVariable("commentId") int commentId) {
        iCommentary.upvoteCommentary(commentId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{commentId}/downvote")
    public ResponseEntity<?> downvoteCommentary(@PathVariable("commentId") int commentId) {
        iCommentary.downvoteCommentary(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{commentId}/likes")
    public ResponseEntity<Integer> getCommentLikes(@PathVariable("commentId") int commentId) {
        int likes = iCommentary.getCommentLikes(commentId);
        return ResponseEntity.ok(likes);
    }
    @GetMapping("/{commentId}/dislikes")
    public ResponseEntity<Integer> getCommentDislikes(@PathVariable("commentId") int commentId) {
        int dislikes = iCommentary.getCommentDislikes(commentId);
        return ResponseEntity.ok(dislikes);
    }

    private CommentaryDTO convertToDto(Commentary com) {
        CommentaryDTO publicationDTO = new CommentaryDTO();
        publicationDTO.setIdCom(com.getIdCom());
        publicationDTO.setDatePublication(com.getDatePublication());
        publicationDTO.setContent(com.getContent());
        publicationDTO.setTags(com.getTags());
        publicationDTO.setVotePositif(com.getVotePositif());
        publicationDTO.setVoteNegatif(com.getVoteNegatif());
        publicationDTO.setSolution(com.getSolution());
        publicationDTO.setIdpub(com.getPublication().getIdpub());
        User user = com.getUser();
        if (user != null) {
            publicationDTO.setUserID(Math.toIntExact(user.getIdUser()));
            publicationDTO.setUsername(user.getNom()+ user.getPrenom());
        } else {
            publicationDTO.setUserID(0);
            publicationDTO.setUsername("");
        }
        return publicationDTO;
    }
}
