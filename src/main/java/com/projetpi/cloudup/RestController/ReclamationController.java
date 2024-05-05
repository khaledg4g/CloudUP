package com.projetpi.cloudup.RestController;

import com.projetpi.cloudup.entities.EtatReclamation;
import com.projetpi.cloudup.entities.Reclamation;
import com.projetpi.cloudup.service.IReclamation;
import com.projetpi.cloudup.service.ReclamationServiceIMP;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reclamation")
@AllArgsConstructor
public class ReclamationController {
    private IReclamation iReclamation;

    @PostMapping("/addreclamation")
    public Reclamation AjouterReclamation(@RequestBody Reclamation reclamation) {
        return iReclamation.AjouterReclamation(reclamation);
    }

    @GetMapping("/getallreclams")
    public List<Reclamation> RetrieveAll() {
        return iReclamation.RetrieveAll();
    }

    @GetMapping("/getidreclam/{id}")
    public Reclamation RetrieveById(@PathVariable Integer id) {
        return iReclamation.RetrieveById(id);
    }

    @PutMapping("/updatereclam")
    public Reclamation UpdateReclamation(@RequestBody Reclamation reclamation) {
        return iReclamation.UpdateReclamation(reclamation);
    }

    @DeleteMapping("/deletereclam/{id}")
    public void DeleteReclamation(@PathVariable Integer id) {
        iReclamation.DeleteReclamation(id);
    }
    @GetMapping("/findobjetrec/{objet}")
    public List<Reclamation> RetrieveObjet(@PathVariable String objet) {
        return iReclamation.RetrieveObjet(objet);
    }

    @GetMapping("/pagination")
    public Page<Reclamation> GetAllPagination(@RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "id") String sortBy,
                                              @RequestParam(defaultValue = "desc") String sortOrder) {
        return iReclamation.GetAllWithPagination(page, size,sortBy,sortOrder);
    }

    @PutMapping("/traitereclam")
    public Reclamation SetReclam(@RequestBody Reclamation reclamation) {
        return iReclamation.SetReclam(reclamation);
    }

    @GetMapping("/getarchivereclam")
    public Page<Reclamation> GetArchives(@RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "desc") String sortOrder) {
        return iReclamation.GetArchives(page,size,sortBy,sortOrder);
    }




}