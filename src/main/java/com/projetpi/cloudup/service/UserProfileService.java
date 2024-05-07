package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.repository.*;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class UserProfileService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UtilisateurRepository userRepository2;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private AwardRepository awardRepository;

    @Transactional
    public void addUserProfile(User user) {
        userRepository.save(user);
        // Add related entities
        addEducations(user);
        addSpecialities(user);
        addAwards(user);
    }
    @Transactional
    public void addUserProfileEducation(User user) {
        userRepository.save(user);
        addEducations(user);
    }

    @Transactional
    public void deleteUserProfile(Long userId) {
        // Delete user profile
        userRepository.deleteById(userId);
        // Delete related entities
        educationRepository.deleteByUserId(userId);
        specialityRepository.deleteByUserId(userId);
        awardRepository.deleteByUserId(userId);
    }

    public User getUserProfile(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    private void addEducations(User user) {
        List<Education> educations = user.getEducations();
        for (Education education : educations) {
            education.setUser(user);
            educationRepository.save(education);
        }
    }

    private void addSpecialities(User user) {
        List<Speciality> specialities = user.getSpecialities();
        for (Speciality speciality : specialities) {
            speciality.setUser(user);
            specialityRepository.save(speciality);
        }
    }

    private void addAwards(User user) {
        List<Award> awards = user.getAwards();
        for (Award award : awards) {
            award.setUser(user);
            awardRepository.save(award);
        }
    }
    @Lock(value = LockModeType.WRITE)
    @Transactional

    public User updateProfile(User user) {
        Optional<User> existingEvenementOptional = userRepository.findById(user.getIdUser());
        if (existingEvenementOptional.isPresent()) {
            User existingEvenement = existingEvenementOptional.get();
            updateEvenementFields(user, existingEvenement);
            return userRepository.save(existingEvenement);
        } else {
            return null;
        }
    }

    private void updateEvenementFields(User updatedUser, User existingUser) {
        for (Field field : updatedUser.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object updatedValue = field.get(updatedUser);
                if (updatedValue != null) {
                    field.set(existingUser, updatedValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
