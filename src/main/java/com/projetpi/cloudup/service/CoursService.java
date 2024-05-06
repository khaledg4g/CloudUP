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

    public PageResponse<CoursResponse> findByName(int page, int size, Authentication connectedUser, String name) {
        User user = (User) connectedUser.getPrincipal();
        long idUser = user.getIdUser();
        Pageable pageable = PageRequest.of(page, size);
        Page<CoursParticuliers> cours = coursRepository.findByNomCoursContainingIgnoreCaseAndProfesseur_IdUser(name, pageable, idUser);
        List<CoursResponse> coursResponses = cours.stream()
                .map(coursMapper::toCoursResponse)
                .toList();
        return new PageResponse<CoursResponse>(
                coursResponses,
                cours.getNumber(),
                cours.getSize(),
                cours.getTotalPages(),
                cours.getTotalElements(),
                cours.isFirst(),
                cours.isLast()
        );
    }


    public PageResponse<CoursResponse> findAllCours(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        Page<CoursParticuliers> cours = coursRepository.findAll(pageable);
        List<CoursResponse> coursResponses = cours.stream()
                .map(coursMapper::toCoursResponse)
                .toList();

        return new PageResponse<>(
                coursResponses,
                cours.getNumber(),
                cours.getSize(),
                cours.getTotalPages(),
                cours.getTotalElements(),
                cours.isFirst(),
                cours.isLast()
        );
    }

    public PageResponse<CoursResponse> findCoursByOwner(int page, int size, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        /* withOwnerId(user.getIdUser()), pageable*/
        Page<CoursParticuliers> cours = coursRepository.findAll(withOwnerId(user.getIdUser()),pageable);
        List<CoursResponse> coursResponses = cours.stream()
                .map(coursMapper::toCoursResponse)
                .toList();

        return new PageResponse<>(
                coursResponses,
                cours.getNumber(),
                cours.getSize(),
                cours.getTotalPages(),
                cours.getTotalElements(),
                cours.isFirst(),
                cours.isLast()
        );

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



    public List<CoursParticuliers> findTopCourses()
    {
        return coursRepository.findCoursWithMostReservations();
    }
    public List<User> findTopProfessor()
    {
        return userRepository.findProfessorsWithMostConfirmedReservations();
    }

}
