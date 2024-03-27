package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Forum;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.categories;
import com.projetpi.cloudup.repository.ForumRepository;
import com.projetpi.cloudup.repository.PublicationRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class ForumServiceIMP implements IForum {
    private ForumRepository forumRepository;
    private PublicationRepository publicationRepository;

    @Autowired
    public ForumServiceIMP( ForumRepository forumRepository, PublicationRepository publicationRepository) {

        this.forumRepository = forumRepository;
        this.publicationRepository = publicationRepository;
    }


    @Override
    public Forum addForum(Forum f) {
        return forumRepository.save(f);
    }

    @Override
    public void deleteForum(Long idf) {
forumRepository.deleteById(idf);
    }

    @Override
    public List<Publication> retrieveAll() {
        return publicationRepository.findAll();
    }

@Override
public List<Publication> retrieveById(int idpub){
        return publicationRepository.findByidpub(idpub);
}



   /* @Override
    public List<Publication> retrieveSubjectDate() {
        List<Forum> forums = forumRepository.findAll();
        List<Publication> publicationsListe = new ArrayList<>();
        for (Forum f : forums) {

            for (Publication p : f.getPublications()) {
                Publication pub = new Publication();
                pub.setSubject(p.getSubject());
                pub.setDatePub(p.getDatePub());

                publicationsListe.add(pub);
            }
        }
        return publicationsListe;
    }*/


    @Override
    public List<Publication> retrieveByTags(@RequestParam String tags
                                                ) {

            return publicationRepository.findBytagsContaining(tags);

    }
  @Override
    public List<Publication> retrieveBySubject (@RequestParam String subject){
        return publicationRepository.findBySubjectContaining(subject);
    }

    @Override
    public List<Publication> retrieveByContent (@RequestParam String content) {
        return publicationRepository.findByContentContaining(content);
    }
    @Override
    public List<Publication> retrieveByCategories(categories categories){
        return publicationRepository.findByCategoriesContaining(categories);
    }
}
