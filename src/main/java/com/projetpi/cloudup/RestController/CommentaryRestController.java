package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Commentary;
import com.projetpi.cloudup.service.ICommentary;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
public class CommentaryRestController {
    @Autowired
    public ICommentary iCommentary;


   @PostMapping("/addCom")
    public Commentary addC (@RequestBody Commentary com){
        return iCommentary.addC(com);
    }
    @PutMapping("/updateC")
    public Commentary updateC (@RequestBody Commentary com){
        return iCommentary.updateC(com);
    }
    @DeleteMapping("/deleteC/{idC}")
    public void deleteC (@PathVariable int idC){
        iCommentary.deleteC(idC);
    }

    @GetMapping("/retrieveALLC")
    public List<Commentary> retrieveAllC (){
        return iCommentary.retrieveAllC();
    }
    @GetMapping("/retrieveByKeyWordsC")
    public List<Commentary> retrieveByKeyWords(@RequestParam String keyWords) {

            return iCommentary.retrieveByKeyWords(keyWords);

    }
    @GetMapping("/retrieveByContentC")
    public List<Commentary> retrieveByContent(@RequestBody String content){
        return iCommentary.findByContent(content);
    }

}
