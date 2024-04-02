package com.projetpi.cloudup.service;
import com.projetpi.cloudup.entities.Collaboration;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.entities.Cours;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ICollaboration {



    public String saveCollaboration(MultipartFile file, String nomcol, String desccol, Date datecol, String placecol, float prixcol,int partenaires_id_part );

        public List<Collaboration> retriveCollaboration();
    public Collaboration addCollaboration(Collaboration col);

    public Collaboration retrieveByIdCollaboration(int idcol);
    public void deleteCollaboration(int idcol);
    Collaboration updateCollaboration(Collaboration collaboration);

    User addCollaborationAndAssignToUser(Collaboration collaboration, int idcol);
    public Cours addCollaborationAndAssignToCours(Cours cours, int idcour);

}
