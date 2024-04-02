package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Collaboration;
import com.projetpi.cloudup.service.CollaborationServiceIMP;
import com.projetpi.cloudup.service.ICollaboration;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:56231"})
@RestController
@AllArgsConstructor

public class CollaborationController {
    @Autowired
    private ICollaboration iCollaboration;



    @PostMapping("/addColbeltaswira")
    public String addCollaboration(@RequestParam("file") MultipartFile file,
                                   @RequestParam("nomcol") String nomcol,
                                   @RequestParam("desccol") String desccol,
                                   @RequestParam("datecol") @DateTimeFormat(pattern = "yyyy-MM-dd") Date datecol,
                                   @RequestParam("placecol") String placecol,
                                   @RequestParam("prixcol") float prixcol,
                                  // @RequestParam("cours_idcour") int cours_idcour,
                                   @RequestParam("partenaires_id_part") int partenaires_id_part)
                                  // @RequestParam("user_iduser") int user_iduser
                                  {
        return iCollaboration.saveCollaboration(file, nomcol,   desccol,   datecol,   placecol,   prixcol,     partenaires_id_part    );
    }






    @PostMapping("/addCollaboration")
    public Collaboration addCollaboration(@RequestBody Collaboration collaboration){
        return iCollaboration.addCollaboration(collaboration);

    }
    @GetMapping("retrieveByIdCollaboration/{idcol}")
    public Collaboration retrieveByIdCollaboration(@PathVariable int idcol){return iCollaboration.retrieveByIdCollaboration(idcol);}


    @GetMapping("/retriveCollaboration")
    public List<Collaboration> retriveCollaboration(){
        return iCollaboration.retriveCollaboration();
    }

    @DeleteMapping("/deleteCollaboration/{idcol}")
    public void deleteCollaboration(@PathVariable int idcol){
        iCollaboration.deleteCollaboration(idcol);
    }


    @PutMapping("/updateCollaboration/{id}")
    public Collaboration updateCol(@RequestBody Collaboration collaboration, @PathVariable int id) {
        collaboration.setIdcol(id); // Ensure ID is set for update
        return iCollaboration.updateCollaboration(collaboration);
    }



}
