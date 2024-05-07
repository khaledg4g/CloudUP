package com.projetpi.cloudup.service;

import com.projetpi.cloudup.commun.PageResponse;
import com.projetpi.cloudup.entities.CoursParticuliers;
import com.projetpi.cloudup.entities.CoursRequest;
import com.projetpi.cloudup.entities.CoursResponse;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.CoursParticuliersRepository;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.projetpi.cloudup.entities.CoursSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
public class CoursService {

    private final CoursParticuliersRepository coursRepository;
    private final UserRepository userRepository;
    private final CoursMapper coursMapper;


    public Long save(CoursRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        CoursParticuliers cours = coursMapper.toCours(request);

        cours.setProfesseur(user);
        return coursRepository.save(cours).getIdCours();
    }

    public Long updateCours(Long CoursID, CoursRequest cours, Authentication connectedUser) {
        CoursParticuliers updatedCours = coursRepository.findById(CoursID)
                .orElseThrow(() -> new EntityNotFoundException("NO COURSE FOUND WITH ID ::" + CoursID));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(updatedCours.getProfesseur().getIdUser(), user.getIdUser())) {
            return null;
        }
        updatedCours.setNomCours(cours.nomCours());
        updatedCours.setDescriptionCours(cours.descriptionCours());
        updatedCours.setType(cours.type());
        updatedCours.setNiveau(cours.niveau());
        updatedCours.setPrice(cours.price());
        updatedCours.setDureeC(cours.dureeC());

        coursRepository.save(updatedCours);
        return updatedCours.getIdCours();


    }

    public CoursResponse findByID(Long idC) {
        return coursRepository.findById(idC)
                .map(coursMapper::toCoursResponse)
                .orElseThrow(() -> new EntityNotFoundException(" No Cours found with the ID ::" + idC));
    }


    public List<CoursResponse> findAllCourses(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<CoursParticuliers> coursParticuliersList = coursRepository.findAll();
        List<CoursResponse> coursResponses = coursParticuliersList.stream().map(coursMapper::toCoursResponse).toList();
        return coursResponses;

    }

    public List<CoursResponse> findByCourseName(Authentication connectedUser, String name) {
        User user = (User) connectedUser.getPrincipal();
        long idUser = user.getIdUser();
        List<CoursParticuliers> coursParticuliersList = coursRepository.findByNomCoursContainingIgnoreCaseAndProfesseur_IdUser(name, idUser);
        List<CoursResponse> coursResponses = coursParticuliersList.stream().map(coursMapper::toCoursResponse).toList();
        return coursResponses;
    }

    public List<CoursResponse> findCoursesByOwner(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<CoursParticuliers> cours = coursRepository.findAll(withOwnerId(user.getIdUser()));
        List<CoursResponse> coursResponses = cours.stream()
                .map(coursMapper::toCoursResponse)
                .toList();
        return coursResponses;

    }

    public void deleteCours(long idC, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        CoursParticuliers cours = coursRepository.findById(idC)
                .orElseThrow(() -> new EntityNotFoundException("No Course found"));
        if (!Objects.equals(cours.getProfesseur().getIdUser(), user.getIdUser())) {
            throw new EntityNotFoundException("NO PERMISSION TO DELETE COURSES");
        }
        coursRepository.deleteById(idC);


    }


    public List<CoursParticuliers> findTopCourses() {
        return coursRepository.findCoursWithMostReservations();
    }

    public List<User> findTopProfessor() {
        return userRepository.findProfessorsWithMostConfirmedReservations();
    }

}
