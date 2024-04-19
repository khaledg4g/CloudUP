package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.EtatReclamation;
import com.projetpi.cloudup.entities.Reclamation;
import com.projetpi.cloudup.repository.ReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ReclamationServiceIMP implements IReclamation{
    private final ReclamationRepository reclamationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final Logger LOGGER = Logger.getLogger(ReclamationServiceIMP.class.getName());




    public ReclamationServiceIMP(ReclamationRepository reclamationRepository, SimpMessagingTemplate messagingTemplate) {
        this.reclamationRepository = reclamationRepository;
        this.simpMessagingTemplate = messagingTemplate;
    }
    @Override
    public Reclamation AjouterReclamation(Reclamation reclamation) {
        reclamation.setTraite(EtatReclamation.EnAttente);
        reclamation.setTime(LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/topic/notification", "New reclam created");
        LOGGER.info("Notification sent successfully");
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> RetrieveAll() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation RetrieveById(Integer id) {
        return reclamationRepository.findById(id).orElse(null);
    }

    @Override
    public Reclamation UpdateReclamation(Reclamation reclamation) {
        Reclamation reclamation1 = reclamationRepository.findById(reclamation.getId()).orElse(null);
        if(reclamation1!=null){
            reclamation1.setId(reclamation.getId());
            reclamation1.setObjet(reclamation.getObjet());
            reclamation1.setDescription(reclamation.getDescription());
            reclamation1.setType(reclamation.getType());
            return reclamationRepository.save(reclamation1);
        }
        return null;
    }

    @Override
    public void DeleteReclamation(Integer id)
    {
        reclamationRepository.deleteById(id);
    }

    @Override
    public List<Reclamation> RetrieveObjet(String objet) {
        return reclamationRepository.findByObjetContaining(objet);
    }

    @Override
        public Page<Reclamation> GetAllWithPagination(int size, int page) {
        return reclamationRepository.findAll(PageRequest.of(size,page));

    }


}
