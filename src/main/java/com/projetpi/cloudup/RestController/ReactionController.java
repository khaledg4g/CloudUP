package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Reactions;
import com.projetpi.cloudup.service.IReaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth/reaction")
public class ReactionController {
    private IReaction reactionService ;

    @GetMapping("/retrieve-all-reactions")
    public List<Reactions> getReactions() {
        return reactionService.retrieveAllReactions();
    }

    @GetMapping("/retrieve-reaction/{reaction-id}")
    public Reactions retrieveReaction(@PathVariable("reaction-id") Long reactionId) {
        return reactionService.retrieveReaction(reactionId);
    }

    @PostMapping("/add-reaction/{post-id}/{user-id}")
    public Reactions addReaction(@RequestBody Reactions reaction, @PathVariable("post-id") Long postId, @PathVariable("user-id") Long userId) {
        return reactionService.addReaction(reaction, postId, userId);
    }

    @DeleteMapping("/remove-reaction/{reaction-id}")
    public void removeReaction(@PathVariable("reaction-id") Long reactionId) {
        reactionService.removeReaction(reactionId);
    }

    @PutMapping("/modify-reaction")
    public Reactions modifyReaction(@RequestBody Reactions reaction) {
        return reactionService.modifyReaction(reaction);
    }

 @GetMapping("/get-reaction/{post-id}/{user-id}")
 public Reactions getReactionByPostAndUser(@PathVariable("post-id") Long postId,@PathVariable("user-id") Long userId ) {
     return reactionService.getReactionByPostAndUser(postId,userId);
 }
    @GetMapping("/count-reaction/{post-id}")
    public Long countReactionsByPost(@PathVariable("post-id") Long postId) {
        return reactionService.countReactionsByPost(postId);
    }
}


