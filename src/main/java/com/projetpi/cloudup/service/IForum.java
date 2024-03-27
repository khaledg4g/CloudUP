package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Forum;
import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.entities.categories;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IForum {
    public Forum addForum (Forum f);
    public void deleteForum (Long idf);
    public List<Publication> retrieveAll ();
    /*public List<Publication> retrieveSubjectDate();*/
    public List<Publication> retrievePById(int id_pub);
    public List<Publication> retrieveByTags(String tags);
    public List<Publication> retrieveBySubject(String subject);
    public List<Publication> retrieveByContent (String content);

    List<Publication> retrieveByCategories(categories categories);
}
