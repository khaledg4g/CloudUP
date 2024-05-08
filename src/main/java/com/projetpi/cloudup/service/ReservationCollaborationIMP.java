package com.projetpi.cloudup.service;
import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.entities.ReservationCollaboration;
import com.projetpi.cloudup.repository.ReservationCollaborationRepository;
import com.projetpi.cloudup.repository.CollaborationRepository;
import com.projetpi.cloudup.repository.PartenairesRepository;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@ComponentScan
@Component
@Service
public class ReservationCollaborationIMP implements IReservationCollaboration {
    @Autowired
    private ReservationCollaborationRepository reservationCollaborationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CollaborationRepository collaborationRepository;

    public ReservationCollaborationIMP(ReservationCollaborationRepository reservationCollaborationRepository) {
        this.reservationCollaborationRepository = reservationCollaborationRepository;
    }


    public ReservationCollaboration addReservationCollaboration(Long userId, int collaborationId) {
        User user = userRepository.findUserByIdUser(userId)
                ;
        Collaboration collaboration = collaborationRepository.findById(collaborationId)
                .orElseThrow(() -> new EntityNotFoundException("Collaboration not found with id: " + collaborationId));

        ReservationCollaboration reservationCollaboration = new ReservationCollaboration();
        reservationCollaboration.setUser(user);
        reservationCollaboration.setCollaboration(collaboration);

        return reservationCollaborationRepository.save(reservationCollaboration);
    }
    @Override
    public List<ReservationCollaboration> getAllReservationCollaborations()  {
        return reservationCollaborationRepository.findAll();
    }

    @Override
    public ReservationCollaboration getReservationCollaborationById(Long id) {
        return reservationCollaborationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ReservationCollaboration not found"));
    }

    @Override
    public void deleteReservationCollaboration(Long id) {
        reservationCollaborationRepository.deleteById(id);
    }


    public List<ReservationCollaboration> findByUserId(Long idUser) {
        return reservationCollaborationRepository.findByUserId(idUser);
    }

}



