package com.projetpi.cloudup.RestController;


import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.service.IForum;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@NoArgsConstructor
public class ForumRestController {
    public IForum iForum;

    @Autowired
    public ForumRestController(IForum iForum) {
        this.iForum = iForum;
    }

    @GetMapping("/retrieveAllP")
    public List<Publication> retrieveAll (){
        return iForum.retrieveAll();
    }
    @GetMapping("/retrieveByKeyWordsP")
    public List<Publication> retrieveByKeyWordsContaining(@RequestParam String keyWords)
    {
        return iForum.retrieveByKeyWords(keyWords);
    }
    @GetMapping("/retrieveBySubject")
    public List<Publication> retrieveBySubject(@RequestParam String subject){
        return iForum.retrieveBySubject(subject);
    }

    @GetMapping("/retrieveByContent")
    public List<Publication> retrieveByContent (@RequestParam String content){
        return iForum.retrieveByContent(content);
    }
}