package com.projetpi.cloudup.RestController;


import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.service.IForum;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@NoArgsConstructor
public class ForumRestController {
    public IForum iForum;

    @GetMapping("/retrieveAllP")
    public List<Publication> retrieveAll (){
        return iForum.retrieveAll();
    }
    @GetMapping("/retrieveByKeyWordsP")
    public List<Publication> retrieveByKeyWordsContaining(@RequestParam String keyWords)
                                                 {
        return iForum.retrieveByKeyWordscontaining(keyWords);

    }
}