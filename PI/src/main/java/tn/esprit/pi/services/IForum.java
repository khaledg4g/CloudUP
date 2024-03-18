package tn.esprit.pi.services;

import tn.esprit.pi.entities.Forum;
import tn.esprit.pi.entities.Publication;

import java.util.List;

public interface IForum {
    public List<Publication> retrieveAll ();
    public List<Publication> retrieveByKeyWords(String keyWords);

}
