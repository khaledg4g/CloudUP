package tn.esprit.pi.services;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Publication;
import tn.esprit.pi.repositories.PublicationRepository;

import java.util.List;
import java.util.Optional;
@Service
@NoArgsConstructor
public class PublicationServiceIMP implements IPublication {

    public PublicationRepository publicationRepository;

    @Override
    public Publication addPub(Publication pub) {
        return publicationRepository.save(pub);
    }

    @Override
    public Publication updatePub(Publication pub) {
        Optional<Publication> existingPubOptional= publicationRepository.findById((long) pub.getId_pub());
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
