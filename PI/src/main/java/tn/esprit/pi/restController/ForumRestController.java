package tn.esprit.pi.restController;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.services.IForum;

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
    public List<Publication> retrieveByKeyWords(@RequestParam String keyWords){
        return iForum.retrieveByKeyWords(keyWords);
    }
}
