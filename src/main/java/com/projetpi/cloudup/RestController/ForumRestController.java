package com.projetpi.cloudup.RestController;


import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.service.IForum;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@NoArgsConstructor
@CrossOrigin("*")
public class ForumRestController {
    public IForum iForum;

    @Autowired
    public ForumRestController(IForum iForum) {
        this.iForum = iForum;
    }

    @PostMapping("/addForum")
    public Forum addForum (@RequestBody Forum f){return iForum.addForum(f);}

    @DeleteMapping("/deleteForum/{idf}")
    public void deleteForum(@PathVariable Long idf) {
        iForum.deleteForum(idf);
    }

    @GetMapping("/retrieveAllPofForum")
    public List<PublicationDTO> retrieveAll (){
        List<Publication> publications = iForum.retrieveAll();
        List<PublicationDTO> publicationDTOs = publications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return publicationDTOs;
    }
@GetMapping("/retrieveByID/{idpub}")
public List<PublicationDTO> retrieveById (@PathVariable int idpub){
        List<Publication> publications = iForum.retrievePById(idpub);
    List<PublicationDTO> publicationDTOs = publications.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    return publicationDTOs;
}


    @GetMapping("/retrieveByTagsP")
    public List<PublicationDTO> retrieveByTagsContaining(@RequestParam String tags)
    {
        List<Publication> publications = iForum.retrieveByTags(tags);
        List<PublicationDTO> publicationDTOs = publications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return publicationDTOs;
    }
    @GetMapping("/retrieveBySubject")
    public List<PublicationDTO> retrieveBySubject(@RequestParam String subject){
        List<Publication> publications=iForum.retrieveBySubject(subject);
        List<PublicationDTO> publicationDTOS= publications.stream()
                .map(this::convertToDto)
                .collect((Collectors.toList()));
        return publicationDTOS;
    }

    @GetMapping("/retrieveByContent")
    public List<PublicationDTO> retrieveByContent (@RequestParam String content){
        List<Publication> publications=iForum.retrieveByContent(content);
        List<PublicationDTO> publicationDTOS= publications.stream()
                .map(this::convertToDto)
                .collect((Collectors.toList()));
        return publicationDTOS;
    }


    @GetMapping("/retrieveByCategory")
    public List<PublicationDTO> retrieveByCategories(@RequestParam categories category) {
        List<Publication> publications = iForum.retrieveByCategories(category);
        List<PublicationDTO> publicationDTOS = publications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return publicationDTOS;
    }

    private PublicationDTO convertToDto(Publication publication) {
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setIdpub(publication.getIdpub());
        publicationDTO.setDatePub(publication.getDatePub());
        publicationDTO.setSubject(publication.getSubject());
        publicationDTO.setContent(publication.getContent());
        publicationDTO.setTags(publication.getTags());
        publicationDTO.setCategories(publication.getCategories());
        publicationDTO.setNbr_vue(publication.getNbr_vue());
        publicationDTO.setNbr_com(publication.getNbr_com());
        publicationDTO.setClosed(publication.getClosed());
        publicationDTO.setForumId(publication.getForum().getId_Forum());
        User user = publication.getUser();
        if (user != null) {
            publicationDTO.setUserId(user.getId());
            publicationDTO.setUsername(user.getNom());
        } else {
            publicationDTO.setUserId(0);
            publicationDTO.setUsername("");
        }
        return publicationDTO;
    }
}