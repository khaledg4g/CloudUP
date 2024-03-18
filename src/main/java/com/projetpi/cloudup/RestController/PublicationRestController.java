package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.service.IPublication;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

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