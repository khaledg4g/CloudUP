package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.Partenaires;
import com.projetpi.cloudup.service.PartenairesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.ResponseEntity;
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@RestController
//@RequestMapping("/api/partenaire")
public class PartenairesController {

    @Autowired
    private PartenairesService service;


    @PostMapping("/addP")
    public String Partenaires(@RequestParam("file") MultipartFile file,
                              @RequestParam("nom") String nom,
                              @RequestParam("descpart") String descpart)

    {

        return service.savePartenairesbeltaswira(file, nom, descpart);
    }



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

    @GetMapping("/partenairesbyname/{name}")
    public Partenaires findPartenairesByNom(@PathVariable String nom){
        return service.getPartenairesBynom(nom);
    }



    @PutMapping("/updatePartenaires/{id}")
    public Partenaires updateEvenement(@RequestBody Partenaires partenaires, @PathVariable int id) {
        partenaires.setId_part(id); // Ensure ID is set for update
        return service.updatePartenaires(partenaires);
    }



    @DeleteMapping("/deletePartenaires/{id}")
    public String deletepartenaires(@PathVariable int id){
        return service.deletePartenaires(id);
    }










}
