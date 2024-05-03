package com.projetpi.cloudup.service;

import com.projetpi.cloudup.email.EmailServer;
import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class ClassListener {
    @Autowired
    private EmailServer emailServer;


    private String previousClosedState;

    @PreUpdate
    public void onPublicationUpdate(Publication publication) {
        // Check if the 'closed' attribute is updated
        if (publication.getClosed() != null && !publication.getClosed().equals(previousClosedState)) {
            // Check if the 'closed' attribute has changed to true
            if ("true".equals(publication.getClosed())) {
                // Trigger email notification
                emailServer.sendEmailNotification(publication.getUser().getEmail());
            }
            // Update previousClosedState to the current value
            previousClosedState = publication.getClosed();
        }
    }

}
