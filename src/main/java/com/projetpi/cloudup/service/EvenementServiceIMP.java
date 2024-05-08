package com.projetpi.cloudup.service;

import com.projetpi.cloudup.email.EmailServer;
import com.projetpi.cloudup.email.EmailServerEvent;
import com.projetpi.cloudup.email.EmailTemplateName;
import com.projetpi.cloudup.entities.CategorieEvenement;
import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.Reactions;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.CategorieEvenementRepository;
import com.projetpi.cloudup.repository.EvenementRepository;
import com.projetpi.cloudup.repository.ReactionsRepository;
import com.projetpi.cloudup.repository.UtilisateurRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
@AllArgsConstructor
public class EvenementServiceIMP implements IEvenement{

    private EvenementRepository evenementRepository;
    private final EmailServerEvent emailServer;
    private ReactionsRepository reactionsRepository;

    private UtilisateurRepository utilisateurRepository;
    private CategorieEvenementRepository categorieEvenementRepository;
    @Override
    public Evenement addEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }
    @Override

    public Evenement retrieveEvenementById(Long idEvenement) {
        return evenementRepository.findById(idEvenement).orElse(null);
    }

    @Lock(value = LockModeType.WRITE)
    @Transactional
    @Override

    public Evenement updateEvenement(Evenement evenement) {
        Optional<Evenement> existingEvenementOptional = evenementRepository.findById(evenement.getId());
        if (existingEvenementOptional.isPresent()) {
            Evenement existingEvenement = existingEvenementOptional.get();
            updateEvenementFields(evenement, existingEvenement);
            return evenementRepository.save(existingEvenement);
        } else {
            return null;
        }
    }

    private void updateEvenementFields(Evenement updatedEvenement, Evenement existingEvenement) {
        for (Field field : updatedEvenement.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object updatedValue = field.get(updatedEvenement);
                if (updatedValue != null) {
                    field.set(existingEvenement, updatedValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    @Override

    public List<Evenement> retrieveAllEvenements() {
        return evenementRepository.findAll();
    }
    @Override

    public void deleteEvenement(Long idEvenement) {
        evenementRepository.deleteById(idEvenement);
    }

//    public Evenement addParticipant(Long evenementId, Long utilisateurId) {
//        Evenement evenement = retrieveEvenementById(evenementId);
//        User participant = utilisateurRepository.findById(utilisateurId).orElseThrow();
//        evenement.getParticipants().add(participant);
//        return evenementRepository.save(evenement);
//    }
//
//    public Evenement removeParticipant(Long evenementId, Long utilisateurId) {
//        Evenement evenement = retrieveEvenementById(evenementId);
//        User participant = utilisateurRepository.findById(utilisateurId).orElseThrow();
//        evenement.getParticipants().remove(participant);
//        return evenementRepository.save(evenement);
//    }
@Override

public Evenement addParticipant(Long evenementId, Long utilisateurId)  {
    Evenement evenement = evenementRepository.findById(evenementId)
            .orElseThrow(() -> new NoSuchElementException("Evenement not found"));

    User participant = utilisateurRepository.findById(utilisateurId)
            .orElseThrow(() -> new NoSuchElementException("User not found"));

    // Check if user is already participating (optional)
    if (evenement.getParticipants().contains(participant)) {
        throw new IllegalArgumentException("User is already participating in this event");
    }

    evenement.getParticipants().add(participant);
    return evenementRepository.save(evenement);
}
    @Override

    public Evenement removeParticipant(Long evenementId, Long utilisateurId)  {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new NoSuchElementException("Evenement not found"));

        User participant = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!evenement.getParticipants().contains(participant)) {
            throw new IllegalArgumentException("User is not participating in this event");
        }

        evenement.getParticipants().remove(participant);
        return evenementRepository.save(evenement);
    }

    public boolean isParticipating(Long evenementId, Long utilisateurId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new NoSuchElementException("Evenement not found"));

        User participant = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return evenement.getParticipants().contains(participant);
    }
    public Evenement updateEventStartDate(Long eventId, LocalDate newStartDate) {
        Optional<Evenement> optionalEvent = evenementRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Evenement event = optionalEvent.get();
            event.setDateDebut(newStartDate);
            return evenementRepository.save(event);
        } else {
            // Handle case where event with eventId is not found
            throw new IllegalArgumentException("User is not  in this event");
        }
    }
    public int getParticipantCount(Long eventId) {
        Evenement event = evenementRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        return event.getParticipants().size();

    }
    @Override

    public List<Evenement> findAllEventsWithParticipantCount() {
        return evenementRepository.findAllByFetchParticipants();
    }
    @Override

    public Evenement reportEvent(Long eventId) {
        Optional<Evenement> eventOptional = evenementRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Evenement event = eventOptional.get();
            event.setReports(event.getReports() + 1);
            if (event.getReports() == 4) {
                sendReportNotificationEmail2(event);
            }
            evenementRepository.save(event);
            return event;
        }
        return null;
    }
    @Override
    public void sendReportNotificationEmail(Evenement event) {
        // Get the organizer of the event
        User organizer = event.getOrganisateur(); // Modify this according to your data model

        // Construct the email message
        String subject = "Event Report Notification";
        String message = "Your event \"" + event.getNom() + "\" has been deleted.";

        // Send the email notification to the organizer
        try {
            emailServer.sendNotificationEmail(
                    organizer.getEmail(),
                    subject,
                    message
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendReportNotificationEmail2(Evenement event) {
        // Get the organizer of the event
        User organizer = event.getOrganisateur(); // Modify this according to your data model

        // Construct the email message
        String subject = "Event Report Notification";
        String message = "Your event \"" + event.getNom() + "\" has received 4 reports.";

        // Send the email notification to the organizer
        try {
            emailServer.sendNotificationEmail(
                    organizer.getEmail(),
                    subject,
                    message
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String saveEvenement(MultipartFile file, String nom, String description, LocalDate dateDebut, LocalDate dateFin,String lieu, long maxparticipant, long organisateur_id_user, long categorie_id) {
        Evenement c = new Evenement();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("Invalid file name");
            return "Invalid file name";
        }
        try {
            c.setImgevent(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        c.setNom(nom);
        c.setDescription(description);
        c.setDateDebut(dateDebut);
        c.setDateFin(dateFin);
        c.setLieu(lieu);
        CategorieEvenement categorie = categorieEvenementRepository.findById(categorie_id).orElse(null);
        c.setCategorie(categorie);
        c.setMaxparticipant(maxparticipant);
        User user = utilisateurRepository.findById(organisateur_id_user).orElse(null);
        c.setOrganisateur(user);

        evenementRepository.save(c);
        return fileName;    }

}

