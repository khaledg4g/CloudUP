package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.repository.CommentaryRepository;
import com.projetpi.cloudup.repository.PublicationRepository;
import com.projetpi.cloudup.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class UserServiceIMP implements IUser{
    public UserRepository userRepository;

    public PublicationRepository publicationRepository;

    public CommentaryRepository commentaryRepository;

    @Autowired
    public UserServiceIMP(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addU(User u) {
        return userRepository.save(u);
    }

    @Override
    public List<Publication> retrieveAllP() {
        return publicationRepository.findAll();
    }

    @Override
    public List<Commentary> retrieveAllC() {
        return commentaryRepository.findAll();
    }
}
