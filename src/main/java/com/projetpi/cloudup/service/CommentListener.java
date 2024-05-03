package com.projetpi.cloudup.service;

import com.projetpi.cloudup.email.EmailServer;
import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentListener {
    @Autowired
    private EmailServer emailServer;


    @PostUpdate
    public void onCommentUpdate(Commentary comment) {
        // Check if the 'closed' attribute has changed to true
        if ("true".equals(comment.getSolution())) {
            // Trigger email notification
            emailServer.sendEmailNotif(comment.getUser().getEmail());
        }
    }
}
