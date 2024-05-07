package com.projetpi.cloudup.Config;

import com.projetpi.cloudup.entities.Forum;
import com.projetpi.cloudup.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class ForumInitializer implements CommandLineRunner {

    private final ForumRepository forumRepository;

    @Autowired
    public ForumInitializer(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    public void run(String... args) {
        Forum forum = forumRepository.findById(1L).orElse(null);
        if (forum == null) {
            forum = new Forum();
            forum.setId_Forum(1);
            forumRepository.save(forum);
        }
    }
}