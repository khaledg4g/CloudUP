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

    @PostUpdate
    public void onPublicationUpdate(Publication publication) {
            if ("true".equals(publication.getClosed()) && publication.getGotMail()==0) {
                publication.setGotMail(1);
                emailServer.sendEmailNotification(publication.getUser().getEmail(), publication.getSubject());
            }
            // Update previousClosedState to the current value

    }


}
