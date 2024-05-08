package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Evenement;
import com.projetpi.cloudup.entities.ReservationCollaboration;

import java.util.List;

public interface IReservationCollaboration {

    public ReservationCollaboration addReservationCollaboration(Long userId, int collaborationId);
    public List<ReservationCollaboration> findByUserId(Long idUser) ;
    public List<ReservationCollaboration> getAllReservationCollaborations() ;
    ReservationCollaboration getReservationCollaborationById(Long id);
    void deleteReservationCollaboration(Long id);
}
