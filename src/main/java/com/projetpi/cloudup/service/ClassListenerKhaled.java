package com.projetpi.cloudup.service;

import com.projetpi.cloudup.email.EmailServer;
import com.projetpi.cloudup.entities.Collaboration;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.projetpi.cloudup.email.EmailServer;
import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ClassListenerKhaled {








    @Autowired
    private EmailServer emailServer;




    @PostUpdate
    public void onCollaborationUpdate(Collaboration collaboration) {
        if ( collaboration.getNbrres()==0) {

            emailServer.sendEmailNotificationcol(collaboration.getUser().getEmail(), collaboration.getNomcol());
        }
        // Update previousClosedState to the current value

    }


}

