package com.projetpi.cloudup.service;
import com.projetpi.cloudup.entities.Collaboration;
import com.projetpi.cloudup.entities.User;
import com.projetpi.cloudup.entities.Cours;
import org.springframework.data.domain.Page;
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

    public List<Collaboration> findCollaborationWithSorting(String nomcol);
    public Page<Collaboration> findCollaborationWithPagination(int offset, int pageSize);
    public Page<Collaboration>GetAllWithPagination(int size,int page);
    public List<Collaboration> RetrieveObjet(String objet);

    public void upvoteCollaboration(int commentId);
    public void downvoteCollaboration(int commentId);
    public void downnumberCollaboration(int idcol);
    public int getCollaborationLikes(int idcol);
    public int getCollaborationDislikes(int idcol);
    public Collaboration getCollaborationFromDatabase(int idcol);
}
