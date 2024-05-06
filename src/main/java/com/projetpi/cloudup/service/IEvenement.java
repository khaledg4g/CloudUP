package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IEvenement {
    public Evenement addEvenement(Evenement evenement);
    public Evenement retrieveEvenementById(Long idEvenement);
    public Evenement updateEvenement(Evenement evenement);
    public List<Evenement> retrieveAllEvenements();
    public void deleteEvenement(Long idEvenement);
    public Evenement addParticipant(Long evenementId, Long utilisateurId);
    public Evenement removeParticipant(Long evenementId, Long utilisateurId);
//    List<Evenement> getTopEvents(int limit);

    List<Evenement> findAllEventsWithParticipantCount();
    public Evenement reportEvent(Long eventId);
    public String saveEvenement(MultipartFile file, String nom, String description, LocalDate dateDebut, LocalDate dateFin, String lieu, long maxparticipant, long organisateur_id_user, long categorie_id);
    public void sendReportNotificationEmail(Evenement event);
}
