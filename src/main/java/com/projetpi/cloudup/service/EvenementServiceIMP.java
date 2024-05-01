package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.EvenementRepository;
import com.projetpi.cloudup.repository.UtilisateurRepository;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class EvenementServiceIMP implements IEvenement{

    private EvenementRepository evenementRepository;

    private UtilisateurRepository utilisateurRepository;
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
            evenementRepository.save(event);
            return event;
        }
        return null;
    }
}

