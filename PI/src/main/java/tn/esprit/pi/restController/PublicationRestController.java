package tn.esprit.pi.restController;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.services.IPublication;

@RestController
@NoArgsConstructor
public class PublicationRestController {
    public IPublication iPublication;
    @PostMapping("/addPub")
    public Publication addPub(@RequestBody Publication pub) {
        return iPublication.addPub(pub);
    }
    @PutMapping("/updatePub")
    public Publication updatePub (@RequestBody Publication pub){
        return iPublication.updatePub(pub);
    }
    @DeleteMapping("/deletePub/{idP}")
    public void deletePub (@PathVariable int idP){
        iPublication.deletePub(idP);
    }
}
