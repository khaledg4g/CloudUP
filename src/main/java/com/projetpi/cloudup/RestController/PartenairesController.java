package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Partenaires;
import com.projetpi.cloudup.service.PartenairesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PartenairesController {

    @Autowired
    private PartenairesService service;

    @PostMapping("/addPartenaires")
    public Partenaires addPartenaires(@RequestBody Partenaires partenaires){

       return service.savePartenaires(partenaires);
    }

    @PostMapping("/addPartenairess")
    public List<Partenaires> addPartenairess(@RequestBody List<Partenaires> partenairess){

        return service.savePartenaires(partenairess);
    }
    @GetMapping("/partenaires")
    public List<Partenaires> findAllPartenaires(){
        return service.getPartenaires();
    }

    @GetMapping("/partenaires/{id}")
    public Partenaires findPartenairesById(@PathVariable int id){
        return service.getPartenairesById(id);
    }

    @GetMapping("/partenaires/{name}")
    public Partenaires findPartenairesByNom(@PathVariable String nom){
        return service.getPartenairesBynom(nom);
    }

    @PutMapping ("/updatePartenaires")
    public Partenaires updatePartenaires(@RequestBody Partenaires partenaires){

        return service.updatePartenaires(partenaires);
    }

    @DeleteMapping("/delete/{id}")
    public String deletepartenaires(int id){
        return service.deletePartenaires(id);
    }


}
