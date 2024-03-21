package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Commentaire;
import com.projetpi.cloudup.repository.CommentaireRepository;
import com.projetpi.cloudup.service.ICommentaire;
import com.projetpi.cloudup.service.IForum;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
public class CommentaireRestController {
    public ICommentaire iCommentaire;


    @PostMapping("/addCom")
    public Commentaire addC (@RequestBody Commentaire com){
        return iCommentaire.addC(com);
    }
    @PutMapping("/updateC")
    public Commentaire updateC (@RequestBody Commentaire com){
        return iCommentaire.updateC(com);
    }
    @DeleteMapping("/deleteC/{idC}")
    public void deleteC (@PathVariable int idC){
        iCommentaire.deleteC(idC);
    }

    @GetMapping("/retrieveALLC")
    public List<Commentaire> retrieveAllC (){
        return iCommentaire.retrieveAllC();
    }
    @GetMapping("/retrieveByKeyWordsC")
    public List<Commentaire> retrieveByKeyWords(@RequestParam String keyWords) {

            return iCommentaire.retrieveByKeyWords(keyWords);

    }

}
