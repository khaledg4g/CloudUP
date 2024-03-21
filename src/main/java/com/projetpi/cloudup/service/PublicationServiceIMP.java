package com.projetpi.cloudup.service;

import com.projetpi.cloudup.entities.Publication;
import com.projetpi.cloudup.repository.PublicationRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class PublicationServiceIMP implements IPublication {
    @Autowired

    public PublicationRepository publicationRepository;


    @Override
    public Publication addPub(Publication pub) {
        return publicationRepository.save(pub);
    }

    @Override
    public Publication updatePub(Publication pub) {
        Optional<Publication> existingPubOptional = publicationRepository.findById((long) pub.getId_pub());
        if (existingPubOptional.isPresent()) {
            Publication existingPub = existingPubOptional.get();
            existingPub.setContenuP(pub.getContenuP());
            existingPub.setKeyWords(pub.getKeyWords());

            return publicationRepository.save(existingPub);
        } else {
            return null;
        }
    }

    @Override
    public void deletePub(int idP) {
        publicationRepository.deleteById((long) idP);
    }


}
