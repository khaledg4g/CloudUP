package com.projetpi.cloudup.Config;

import com.projetpi.cloudup.entities.Forum;
import com.projetpi.cloudup.repository.ForumRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    private final ForumRepository forumRepository;

    public DataInitializer(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            if (!forumRepository.existsById(1L)) {
                Forum forum = new Forum();
                forum.setId_Forum(1);
                forum.setNbr_pub(0);
                forumRepository.save(forum);
            }
        };
    }
}
